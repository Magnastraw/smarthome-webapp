package com.netcracker.smarthome.business.endpoints.transformators.policyframework;

import com.netcracker.smarthome.business.endpoints.jsonentities.JsonMetric;
import com.netcracker.smarthome.business.endpoints.services.SmartObjectService;
import com.netcracker.smarthome.business.endpoints.transformators.ITransformator;
import com.netcracker.smarthome.business.policy.events.EventType;
import com.netcracker.smarthome.business.policy.events.MetricEvent;
import com.netcracker.smarthome.business.specs.MetricSpecService;
import org.springframework.beans.factory.annotation.Autowired;

public class PolicyMetricEventTransformator implements ITransformator<MetricEvent,JsonMetric> {
    private final SmartObjectService smartObjectService;
    private final MetricSpecService metricSpecService;

    @Autowired
    public PolicyMetricEventTransformator(SmartObjectService smartObjectService, MetricSpecService metricSpecService) {
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

    public JsonMetric toJsonEntity(MetricEvent Entity) {
        throw new UnsupportedOperationException("Not supported");
    }
}
