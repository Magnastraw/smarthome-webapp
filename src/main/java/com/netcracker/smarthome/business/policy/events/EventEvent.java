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

    protected EventEvent(EventType type, SmartObject object, SmartObject subobject, Timestamp registryDate, AlarmSpec spec, Event dbEvent, AlarmSeverity severity) {
        super(type, object, subobject, registryDate, spec, dbEvent);
        this.severity = severity;
    }

    public AlarmSeverity getSeverity() {
        return severity;
    }

    public void setSeverity(AlarmSeverity severity) {
        this.severity = severity;
    }

    @Override
    public String toString() {
        return String.format("Event [\n\tregistry time: %s\n\tobject: %s;\n\tsubobject: %s;\n\tevent type: %s;\n\tseverity: %s\n]",
                getRegistryDate(),
                getObject().getName(),
                getSubobject() == null ? "none" : getSubobject().getName(),
                getSpec().getSpecName(),
                getSeverity() == null ? "none" : getSeverity().toString()
        );
    }
}
