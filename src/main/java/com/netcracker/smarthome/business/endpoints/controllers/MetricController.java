package com.netcracker.smarthome.business.endpoints.controllers;

import com.netcracker.smarthome.business.endpoints.JsonRestParser;
import com.netcracker.smarthome.business.endpoints.jsonentities.JsonMetric;
import com.netcracker.smarthome.business.endpoints.transformators.MetricTransformator;
import com.netcracker.smarthome.business.endpoints.transformators.policyframework.PolicyMetricEventTransformator;
import com.netcracker.smarthome.business.policy.core.PolicyEngine;
import com.netcracker.smarthome.business.policy.events.MetricEvent;
import com.netcracker.smarthome.business.services.HomeService;
import com.netcracker.smarthome.business.services.MetricService;
import com.netcracker.smarthome.business.services.MetricSpecService;
import com.netcracker.smarthome.business.services.SmartObjectService;
import com.netcracker.smarthome.model.entities.Metric;
import com.netcracker.smarthome.model.entities.MetricHistory;
import com.netcracker.smarthome.model.entities.SmartHome;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@RestController
public class MetricController {
    private static final Logger LOG = LoggerFactory.getLogger(MetricController.class);
    @Autowired
    PolicyEngine policyEngine;
    @Autowired
    private HomeService homeService;
    @Autowired
    private SmartObjectService smartObjectService;
    @Autowired
    private MetricService metricService;
    @Autowired
    private MetricSpecService metricSpecService;

    @RequestMapping(value = "/metrics",
            method = RequestMethod.POST,
            consumes = "application/json")
    public ResponseEntity sendHomeParams(@RequestParam(value = "houseId", required = true) String houseId,
                                         @RequestBody String json) {
        LOG.info("POST /metrics\nBody:\n" + json);
        SmartHome home = homeService.getHomeBySecretKey(houseId);
        if (home == null)
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        JsonRestParser parser = new JsonRestParser();
        List<JsonMetric> metrics;
        try {
            metrics = parser.parseMetrics(json);
        } catch (IOException e) {
            LOG.error("Error during parsing", e);
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        if (metrics.size() == 0)
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        MetricTransformator metricTransformator = new MetricTransformator(smartObjectService, metricService);
        PolicyMetricEventTransformator policyEventTransformator = new PolicyMetricEventTransformator(smartObjectService, metricSpecService);
        for (JsonMetric item : metrics) {
            item.setSmartHomeId(home.getSmartHomeId());
            Metric metric = metricTransformator.fromJsonEntity(item);
            Metric existingMetric = metricService.getMetric(home.getSmartHomeId(), metric.getObject().getSmartObjectId(), metric.getSubobject() != null ? metric.getSubobject().getSmartObjectId() : null, item.getSpecId());
            try {
                if (existingMetric != null) {
                    metric.setMetricId(existingMetric.getMetricId());
                } else {
                    metricService.saveMetric(metric);
                }

                MetricHistory metricHistory = new MetricHistory(item.getRegistryDate(), BigDecimal.valueOf(item.getValue()), metric);
                metricService.saveMetricValue(metricHistory);

                MetricEvent policyMetricEvent = policyEventTransformator.fromJsonEntity(item);
                policyEngine.handleEvent(policyMetricEvent);
            } catch (Exception ex) {
                LOG.error("Error during saving of data", ex);
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
