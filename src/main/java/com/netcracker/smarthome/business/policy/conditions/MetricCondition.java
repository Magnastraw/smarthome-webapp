package com.netcracker.smarthome.business.policy.conditions;

import com.netcracker.smarthome.ApplicationContextHolder;
import com.netcracker.smarthome.business.policy.events.EventType;
import com.netcracker.smarthome.business.policy.events.MetricEvent;
import com.netcracker.smarthome.business.policy.events.PolicyEvent;
import com.netcracker.smarthome.business.services.MetricService;

import java.util.Map;

public abstract class MetricCondition implements PolicyCondition {
    private long metric;
    private long policy;
    private long object;

    private MetricService metricService;

    public MetricCondition(Map<String, String> params) {
        this.metric = Long.parseLong(params.get("metric"));
        this.object = params.containsKey("object") ? Long.parseLong(params.get("object")) : -1;
        this.policy = Long.parseLong(params.get("policy"));
        metricService = ApplicationContextHolder.getApplicationContext().getBean(MetricService.class);
    }

    public Boolean evaluate(PolicyEvent event) {
        if (event.getType() != EventType.METRIC)
            return null;
        if (checkMatching(event))
            return evaluate(((MetricEvent) event).getValue());
        Double lastValue = loadLastMetricValue();
        return lastValue != null && evaluate(lastValue);
    }

    private boolean checkMatching(PolicyEvent event) {
        long eventObject = event.getSubobject() == null ? event.getObject().getSmartObjectId() : event.getSubobject().getSmartObjectId();
        return metric == event.getSpec().getSpecId() && (!isInline() || object == eventObject);
    }

    private Double loadLastMetricValue() {
        if (isInline())
            return metricService.getLastMetricValueByObject(object, metric);
        return metricService.getLastMetricValueByPolicy(policy, metric);
    }

    private boolean isInline() {
        return object > 0;
    }

    protected abstract boolean evaluate(double value);
}
