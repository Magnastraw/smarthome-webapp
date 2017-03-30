package com.netcracker.smarthome.business.policy.conditions;

public class StrictRangeCondition implements MetricCondition {
    public boolean evaluate() {
        return false;
    }

    public boolean isInline() {
        return false;
    }
}
