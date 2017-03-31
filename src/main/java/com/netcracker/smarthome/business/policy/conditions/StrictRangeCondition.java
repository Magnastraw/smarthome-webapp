package com.netcracker.smarthome.business.policy.conditions;

import com.netcracker.smarthome.business.MetricService;
import com.netcracker.smarthome.model.entities.MetricSpec;
import com.netcracker.smarthome.model.entities.Policy;
import com.netcracker.smarthome.model.entities.SmartObject;

public class StrictRangeCondition extends MetricCondition {
    private double minValue, maxValue;

    public StrictRangeCondition() {
    }

    public StrictRangeCondition(MetricService service, MetricSpec metric, SmartObject object, Policy policy, double minValue, double maxValue) {
        super(service, metric, object, policy);
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public StrictRangeCondition(double minValue, double maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    protected boolean evaluate(double value) {
        return minValue < value && value < maxValue;
    }
}
