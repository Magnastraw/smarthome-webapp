package com.netcracker.smarthome.business.policy;

import com.netcracker.smarthome.model.entities.AlarmSpec;
import com.netcracker.smarthome.model.entities.SmartObject;

import java.util.Date;

public class EventEvent extends Event {
    private int severity;

    public EventEvent() {
    }

    public EventEvent(EventType type, SmartObject object, SmartObject subobject, Date registryDate, AlarmSpec spec, int severity) {
        super(type, object, subobject, registryDate, spec);
        this.severity = severity;
    }

    public int getSeverity() {
        return severity;
    }

    public void setSeverity(int severity) {
        this.severity = severity;
    }
}
