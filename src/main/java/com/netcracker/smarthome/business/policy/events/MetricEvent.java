package com.netcracker.smarthome.business.policy.events;

import com.netcracker.smarthome.model.entities.MetricSpec;
import com.netcracker.smarthome.model.entities.SmartObject;

import java.sql.Timestamp;

public class MetricEvent extends Event {
    private double value;

    public MetricEvent() {
    }

    public MetricEvent(EventType type, SmartObject object, SmartObject subobject, Timestamp registryDate, MetricSpec spec, com.netcracker.smarthome.model.entities.Event dbEvent, double value) {
        super(type, object, subobject, registryDate, spec, dbEvent);
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
