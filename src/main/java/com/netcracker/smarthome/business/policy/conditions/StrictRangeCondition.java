package com.netcracker.smarthome.business.policy.conditions;

import java.util.HashMap;

public class StrictRangeCondition extends MetricCondition {
    private double minValue, maxValue;

    public StrictRangeCondition() {
    }

    public StrictRangeCondition(HashMap<String, String> params) {
        super(params);
        this.minValue = Double.parseDouble(params.get("minValue"));
        this.maxValue = Double.parseDouble(params.get("maxValue"));
    }

    public StrictRangeCondition(double minValue, double maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    protected boolean evaluate(double value) {
        return minValue < value && value < maxValue;
    }
}
