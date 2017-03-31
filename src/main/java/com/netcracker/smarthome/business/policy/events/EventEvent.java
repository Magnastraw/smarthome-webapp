package com.netcracker.smarthome.business.policy.events;

import com.netcracker.smarthome.model.entities.AlarmSpec;
import com.netcracker.smarthome.model.entities.SmartObject;

import java.sql.Timestamp;

// TODO: add event type
public class EventEvent extends Event {
    private Integer severity;

    public EventEvent() {
    }

    public EventEvent(EventType type, SmartObject object, SmartObject subobject, Timestamp registryDate, AlarmSpec spec, Integer severity) {
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
