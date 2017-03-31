package com.netcracker.smarthome.business.policy.conditions;

import com.netcracker.smarthome.business.MetricService;
import com.netcracker.smarthome.dal.repositories.MetricRepository;
import com.netcracker.smarthome.model.entities.MetricSpec;
import com.netcracker.smarthome.model.entities.Policy;
import com.netcracker.smarthome.model.entities.SmartObject;

public class NonstrictRangeCondition extends MetricCondition {
    private double minValue, maxValue;

    public NonstrictRangeCondition() {
    }

    public NonstrictRangeCondition(MetricService service, MetricSpec metric, SmartObject object, Policy policy, double minValue, double maxValue) {
        super(service, metric, object, policy);
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public NonstrictRangeCondition(double minValue, double maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    protected boolean evaluate(double value) {
        return minValue <= value && value <= maxValue;
    }
}
