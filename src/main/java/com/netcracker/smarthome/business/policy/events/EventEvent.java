package com.netcracker.smarthome.business.policy.events;

import com.netcracker.smarthome.model.entities.AlarmSpec;
import com.netcracker.smarthome.model.entities.SmartObject;
import com.netcracker.smarthome.model.entities.Spec;
import com.netcracker.smarthome.model.enums.AlarmSeverity;

import java.sql.Timestamp;

public class EventEvent extends Event {
    private AlarmSeverity severity;

    public EventEvent() {
    }

    public EventEvent(EventType type, SmartObject object, SmartObject subobject, Timestamp registryDate, AlarmSpec spec, long dbEventId, AlarmSeverity severity) {
        super(type, object, subobject, registryDate, spec, dbEventId);
        this.severity = severity;
    }

    public AlarmSeverity getSeverity() {
        return severity;
    }

    public void setSeverity(AlarmSeverity severity) {
        this.severity = severity;
    }
}
