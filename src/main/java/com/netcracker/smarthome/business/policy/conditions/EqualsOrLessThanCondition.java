package com.netcracker.smarthome.business.policy.conditions;

import java.util.Map;

public class EqualsOrLessThanCondition extends MetricCondition {
    private double value;

    public EqualsOrLessThanCondition() {
    }

    public EqualsOrLessThanCondition(Map<String, String> params) {
        super(params);
        this.value = Double.parseDouble(params.get("value"));
    }

    protected boolean evaluate(double value) {
        return value <= this.value;
    }
}
