package com.netcracker.smarthome.business.policy.conditions;

import java.util.HashMap;

public class LessThanCondition extends MetricCondition {
    private double value;

    public LessThanCondition() {
    }

    public LessThanCondition(HashMap<String, String> params) {
        super(params);
        this.value = Double.parseDouble(params.get("value"));
    }

    protected boolean evaluate(double value) {
        return value < this.value;
    }
}
