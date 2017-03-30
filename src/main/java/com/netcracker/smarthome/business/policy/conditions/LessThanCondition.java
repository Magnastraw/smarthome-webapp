package com.netcracker.smarthome.business.policy.conditions;

public class LessThanCondition extends MetricCondition {
    public boolean evaluate() {
        return false;
    }

    public boolean isInline() {
        return false;
    }
}
