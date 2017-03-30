package com.netcracker.smarthome.business.policy.conditions;

public class NonstrictRangeCondition implements MetricCondition {
    public boolean evaluate() {
        return false;
    }

    public boolean isInline() {
        return false;
    }
}
