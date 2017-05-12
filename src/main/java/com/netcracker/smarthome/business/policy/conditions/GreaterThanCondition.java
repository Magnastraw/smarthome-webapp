package com.netcracker.smarthome.business.policy.conditions;

import java.util.Map;

public class GreaterThanCondition extends MetricCondition {
    private double value;

    public GreaterThanCondition(Map<String, String> params) {
        super(params);
        this.value = Double.parseDouble(params.get("value"));
    }

    protected boolean evaluate(double value) {
        return value > this.value;
    }
}
