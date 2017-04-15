package com.netcracker.smarthome.business.endpoints.transformators;

import com.netcracker.smarthome.business.HomeService;
import com.netcracker.smarthome.business.endpoints.jsonentities.JsonMetric;
import com.netcracker.smarthome.business.endpoints.services.MetricService;
import com.netcracker.smarthome.business.endpoints.services.SmartObjectService;
import com.netcracker.smarthome.model.entities.Metric;
import org.springframework.beans.factory.annotation.Autowired;

public class MetricTransformator implements ITransformator<Metric,JsonMetric> {

    @Autowired
    private HomeService homeService;
    @Autowired
    private final SmartObjectService smartObjectService;
    @Autowired
    private final MetricService metricService;

    public MetricTransformator(SmartObjectService smartObjectService, MetricService metricService) {
        this.smartObjectService = smartObjectService;
        this.metricService = metricService;
    }
    public Metric fromJsonEntity(JsonMetric jsonEntity) {
        Metric metric = new Metric();
        metric.setObject(smartObjectService.getObjectByExternalKey(jsonEntity.getSmartHomeId(), jsonEntity.getObjectId()));
        metric.setSubobject(smartObjectService.getObjectByExternalKey(jsonEntity.getSmartHomeId(), jsonEntity.getSubobjectId()));
        metric.setMetricSpec(metricService.getMetricSpecById(jsonEntity.getSpecId()));
        metric.setSmartHome(metric.getObject().getSmartHome());
        return metric;
    }
}
