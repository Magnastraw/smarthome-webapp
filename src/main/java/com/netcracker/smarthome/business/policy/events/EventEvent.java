package com.netcracker.smarthome.business.policy.events;

import com.netcracker.smarthome.model.entities.AlarmSpec;
import com.netcracker.smarthome.model.entities.SmartObject;
import com.netcracker.smarthome.model.enums.AlarmSeverity;
import java.sql.Timestamp;

public class EventEvent extends Event {
    private AlarmSeverity severity;

    public EventEvent() {
    }

    public EventEvent(EventType type, SmartObject object, SmartObject subobject, Timestamp registryDate, AlarmSpec spec, AlarmSeverity severity, com.netcracker.smarthome.model.entities.Event dbEvent) {
        super(type, object, subobject, registryDate, spec, dbEvent);
        this.severity = severity;
    }

    public AlarmSeverity getSeverity() {
        return severity;
    }

    public void setSeverity(AlarmSeverity severity) {
        this.severity = severity;
    }
}