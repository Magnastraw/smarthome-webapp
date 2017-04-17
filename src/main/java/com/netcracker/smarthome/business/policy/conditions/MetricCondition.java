package com.netcracker.smarthome.business.policy.conditions;

import com.netcracker.smarthome.business.MetricService;
import com.netcracker.smarthome.business.policy.events.Event;
import com.netcracker.smarthome.business.policy.events.EventType;
import com.netcracker.smarthome.business.policy.events.MetricEvent;

import java.util.HashMap;

public abstract class MetricCondition implements Condition {
    private MetricService service;
    private long metric;
    private long policy;
    private long object;

    public MetricCondition() {
    }

    public MetricCondition(HashMap<String, String> params) {
        this.metric = Long.parseLong(params.get("metric"));
        this.object = params.containsKey("object") ? Long.parseLong(params.get("object")) : -1;
        this.policy = Long.parseLong(params.get("policy"));
    }

    public boolean evaluate(Event event) {
        if (!checkMatching(event)) {
            double lastValue = loadLastMetricValue();
            return evaluate(lastValue);
        }
        return evaluate(((MetricEvent) event).getValue());
    }

    private boolean checkMatching(Event event) {
        return event.getType().equals(EventType.METRIC) && metric == event.getSpec().getSpecId() && (!isInline() || object == event.getSubobject().getSmartObjectId());
    }

    private double loadLastMetricValue() {
        if (isInline())
            return service.getLastMetricValueByObject(object, metric);
        return service.getLastMetricValueByPolicy(policy, metric);
    }

    private boolean isInline() {
        return object > 0;
    }

    protected abstract boolean evaluate(double value);
}
