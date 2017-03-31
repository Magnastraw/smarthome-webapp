package com.netcracker.smarthome.business.policy.conditions;

import com.netcracker.smarthome.business.MetricService;
import com.netcracker.smarthome.business.policy.events.Event;
import com.netcracker.smarthome.business.policy.events.EventType;
import com.netcracker.smarthome.business.policy.events.MetricEvent;
import com.netcracker.smarthome.model.entities.MetricSpec;
import com.netcracker.smarthome.model.entities.Policy;
import com.netcracker.smarthome.model.entities.SmartObject;

public abstract class MetricCondition implements Condition {
    private MetricService service;
    private MetricSpec metric;
    private SmartObject object;
    private Policy policy;

    public MetricCondition() {
    }

    public MetricCondition(MetricService service, MetricSpec metric, SmartObject object, Policy policy) {
        this.service = service;
        this.metric = metric;
        this.object = object;
        this.policy = policy;
    }

    public boolean evaluate(Event event) {
        if (!eventCorrespond(event)) {
            double lastValue = loadLastMetricValue();
            return evaluate(lastValue);
        }
        return evaluate(((MetricEvent) event).getValue());
    }

    private boolean eventCorrespond(Event event) {
        return event.getType().equals(EventType.METRIC) && metric.equals(event.getSpec()) && (!isInline() || object.equals(event.getSubobject()));
    }

    private double loadLastMetricValue() {
        if (isInline())
            return service.getLastMetricValue(object, metric);
        return service.getLastMetricValue(policy, metric);
    }

    private boolean isInline() {
        return object != null;
    }

    protected abstract boolean evaluate(double value);
}
