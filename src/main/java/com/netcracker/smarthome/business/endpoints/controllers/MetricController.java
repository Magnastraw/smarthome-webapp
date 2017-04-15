package com.netcracker.smarthome.business.endpoints.controllers;

import com.netcracker.smarthome.business.HomeService;
import com.netcracker.smarthome.business.endpoints.JsonRestParser;
import com.netcracker.smarthome.business.endpoints.jsonentities.JsonMetric;
import com.netcracker.smarthome.business.endpoints.services.MetricService;
import com.netcracker.smarthome.business.endpoints.services.SmartObjectService;
import com.netcracker.smarthome.business.endpoints.transformators.MetricEventTransformator;
import com.netcracker.smarthome.business.endpoints.transformators.MetricTransformator;
import com.netcracker.smarthome.business.policy.events.MetricEvent;
import com.netcracker.smarthome.business.specs.MetricSpecService;
import com.netcracker.smarthome.model.entities.Metric;
import com.netcracker.smarthome.model.entities.MetricHistory;
import com.netcracker.smarthome.model.entities.SmartHome;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class MetricController {
    private static final Logger LOG = LoggerFactory.getLogger(MetricController.class);

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
    public ResponseEntity sendHomeParams(@RequestParam(value="houseId", required=true) long houseId,
                                         @RequestBody String json) {
        SmartHome home = homeService.getHomeById(houseId);
        if (home == null)
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        JsonRestParser parser = new JsonRestParser();
        List<JsonMetric> metrics = parser.parseMetrics(json);
        for (JsonMetric item : metrics) {
            try {
                item.setSmartHomeId(houseId);
                MetricTransformator transf = new MetricTransformator(smartObjectService, metricService);
                Metric metric = transf.fromJsonEntity(item);

                /*Metric metric = new Metric();
                metric.setSmartHome(home);
                metric.setObject(smartObjectService.getObjectByExternalKey(houseId, item.getObjectId()));
                metric.setSubobject(smartObjectService.getObjectByExternalKey(houseId, item.getSubobjectId()));
                metric.setMetricSpec(metricService.getMetricSpecById(item.getSpecId()));*/
                Metric existingMetric = metricService.getMetric(houseId, metric.getObject().getSmartObjectId(), item.getSpecId());
                if (existingMetric != null) {
                    metric.setMetricId(existingMetric.getMetricId());
                } else {
                    metricService.saveMetric(metric);
                }
                MetricHistory metricHistory = new MetricHistory(item.getRegistryDate(), BigDecimal.valueOf(item.getValue()), metric);
                metricService.saveMetricValue(metricHistory);

                MetricEventTransformator transformator = new MetricEventTransformator(smartObjectService, metricSpecService);
                MetricEvent metricEvent = transformator.fromJsonEntity(item);
            }
            catch (Exception ex) {
                LOG.error("Error during saving of data", ex);
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        }
        /* */
        return new ResponseEntity(HttpStatus.OK);
    }
}
