package com.netcracker.smarthome.business.policy.conditions;

import java.util.HashMap;

public class EqualsOrGreaterThanCondition extends MetricCondition {
    private double value;

    public EqualsOrGreaterThanCondition() {
    }

    public EqualsOrGreaterThanCondition(HashMap<String, String> params) {
        super(params);
        this.value = Double.parseDouble(params.get("value"));
    }

    protected boolean evaluate(double value) {
        return value >= this.value;
    }
}
