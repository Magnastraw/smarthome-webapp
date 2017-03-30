package com.netcracker.smarthome.business.policy.conditions;

public abstract class MetricCondition implements Condition {
    public abstract boolean evaluate();
    public abstract boolean isInline();
}
