package com.netcracker.smarthome.business.policy.conditions;

import com.netcracker.smarthome.ApplicationContextHolder;
import com.netcracker.smarthome.business.MetricService;
import com.netcracker.smarthome.business.policy.events.EventType;
import com.netcracker.smarthome.business.policy.events.MetricEvent;
import com.netcracker.smarthome.business.policy.events.PolicyEvent;
import com.netcracker.smarthome.model.entities.SmartObject;

import java.util.HashMap;

public abstract class MetricCondition implements Condition {
    private long metric;
    private long policy;
    private long object;

    private MetricService metricService;

    public MetricCondition() {
        metricService = ApplicationContextHolder.getApplicationContext().getBean(MetricService.class);
    }

    public MetricCondition(HashMap<String, String> params) {
        this.metric = Long.parseLong(params.get("metric"));
        this.object = params.containsKey("object") ? Long.parseLong(params.get("object")) : -1;
        this.policy = Long.parseLong(params.get("policy"));
        metricService = ApplicationContextHolder.getApplicationContext().getBean(MetricService.class);
    }

    public boolean evaluate(PolicyEvent event) {
        if (!checkMatching(event)) {
            double lastValue = loadLastMetricValue();
            return evaluate(lastValue);
        }
        return evaluate(((MetricEvent) event).getValue());
    }

    private boolean checkMatching(PolicyEvent event) {
        return event.getType().equals(EventType.METRIC) && metric == event.getSpec().getSpecId() && (!isInline() || object == getEventObject(event).getSmartObjectId());
    }

    private SmartObject getEventObject(PolicyEvent event) {
        return event.getSubobject() == null ? event.getObject() : event.getSubobject();
    }

    private double loadLastMetricValue() {
        if (isInline())
            return metricService.getLastMetricValueByObject(object, metric);
        return metricService.getLastMetricValueByPolicy(policy, metric);
    }

    private boolean isInline() {
        return object > 0;
    }

    protected abstract boolean evaluate(double value);
}
