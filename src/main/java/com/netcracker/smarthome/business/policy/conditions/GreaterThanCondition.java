package com.netcracker.smarthome.business.policy.conditions;

import com.netcracker.smarthome.business.MetricService;
import com.netcracker.smarthome.model.entities.MetricSpec;
import com.netcracker.smarthome.model.entities.Policy;
import com.netcracker.smarthome.model.entities.SmartObject;

public class GreaterThanCondition extends MetricCondition {
    private double value;

    public GreaterThanCondition() {
    }

    public GreaterThanCondition(MetricService service, MetricSpec metric, SmartObject object, Policy policy, double value) {
        super(service, metric, object, policy);
        this.value = value;
    }

    protected boolean evaluate(double value) {
        return value > this.value;
    }
}
