package com.netcracker.smarthome.business.endpoints.transformators;

import com.netcracker.smarthome.business.endpoints.jsonentities.JsonMetric;
import com.netcracker.smarthome.business.endpoints.services.SmartObjectService;
import com.netcracker.smarthome.business.policy.events.EventType;
import com.netcracker.smarthome.business.policy.events.MetricEvent;
import com.netcracker.smarthome.business.specs.MetricSpecService;
import org.springframework.beans.factory.annotation.Autowired;

public class MetricEventTransformator implements ITransformator<MetricEvent,JsonMetric> {

    @Autowired
    private final SmartObjectService smartObjectService;
    @Autowired
    private final MetricSpecService metricSpecService;

    public MetricEventTransformator(SmartObjectService smartObjectService, MetricSpecService metricSpecService) {
        this.smartObjectService = smartObjectService;
        this.metricSpecService = metricSpecService;
    }
    public MetricEvent fromJsonEntity(JsonMetric jsonEntity) {
        MetricEvent metricEvent = new MetricEvent();
        metricEvent.setRegistryDate(jsonEntity.getRegistryDate());
        metricEvent.setValue(jsonEntity.getValue());
        metricEvent.setType(EventType.METRIC);
        metricEvent.setObject(smartObjectService.getObjectByExternalKey(jsonEntity.getSmartHomeId(), jsonEntity.getObjectId()));
        metricEvent.setSubobject(smartObjectService.getObjectByExternalKey(jsonEntity.getSmartHomeId(), jsonEntity.getSubobjectId()));
        metricEvent.setSpec(metricSpecService.getMetricSpecById(jsonEntity.getSpecId()));
        return metricEvent;
    }
}
