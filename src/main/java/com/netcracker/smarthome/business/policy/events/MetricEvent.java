package com.netcracker.smarthome.business.policy.events;

import com.netcracker.smarthome.model.entities.MetricSpec;
import com.netcracker.smarthome.model.entities.SmartObject;

import java.util.Date;

public class MetricEvent extends Event {
    private double value;

    public MetricEvent() {
    }

    public MetricEvent(EventType type, SmartObject object, SmartObject subobject, Date registryDate, double value, MetricSpec spec) {
        super(type, object, subobject, registryDate, spec);
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
