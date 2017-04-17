package com.netcracker.smarthome.business.policy.events;

import com.netcracker.smarthome.model.entities.AlarmSpec;
import com.netcracker.smarthome.model.entities.Event;
import com.netcracker.smarthome.model.entities.SmartObject;
import com.netcracker.smarthome.model.enums.AlarmSeverity;

import java.sql.Timestamp;

public class EventEvent extends PolicyEvent {
    private AlarmSeverity severity;

    public EventEvent() {
    }

    public EventEvent(SmartObject object, SmartObject subobject, Timestamp registryDate, AlarmSpec spec, Event dbEvent, AlarmSeverity severity) {
        super(EventType.EVENT, object, subobject, registryDate, spec, dbEvent);
        this.severity = severity;
    }

    public AlarmSeverity getSeverity() {
        return severity;
    }

    public void setSeverity(AlarmSeverity severity) {
        this.severity = severity;
    }
}
