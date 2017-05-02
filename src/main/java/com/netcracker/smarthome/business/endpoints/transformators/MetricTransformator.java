package com.netcracker.smarthome.business.endpoints.transformators;

import com.netcracker.smarthome.business.endpoints.jsonentities.JsonMetric;
import com.netcracker.smarthome.business.services.MetricService;
import com.netcracker.smarthome.business.services.SmartObjectService;
import com.netcracker.smarthome.model.entities.Metric;
import org.springframework.beans.factory.annotation.Autowired;

public class MetricTransformator implements ITransformator<Metric,JsonMetric> {
    private final SmartObjectService smartObjectService;
    private final MetricService metricService;

    @Autowired
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

    public JsonMetric toJsonEntity(Metric Entity) {
        throw new UnsupportedOperationException("Not supported");
    }
}
