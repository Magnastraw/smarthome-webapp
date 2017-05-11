package com.netcracker.smarthome.business.policy.events;

import com.netcracker.smarthome.model.entities.AlarmSpec;
import com.netcracker.smarthome.model.entities.Event;
import com.netcracker.smarthome.model.entities.SmartObject;
import com.netcracker.smarthome.model.enums.AlarmSeverity;

import java.sql.Timestamp;

public class AlarmEvent extends EventEvent {
    private Timestamp severityChangeTime;
    private long clearedUserId;

    public AlarmEvent() {
    }

    public AlarmEvent(PolicyEvent causalEvent, AlarmSpec spec, Event dbEvent, AlarmSeverity severity, Timestamp severityChangeTime, long clearedUserId) {
        super(EventType.ALARM, causalEvent.getObject(), causalEvent.getSubobject(), causalEvent.getRegistryDate(), spec, dbEvent, severity);
        this.severityChangeTime = severityChangeTime;
        this.clearedUserId = clearedUserId;
    }

    public AlarmEvent(SmartObject object, SmartObject subobject, Timestamp registryDate, AlarmSpec spec, Event dbEvent, AlarmSeverity severity, Timestamp severityChangeTime, long clearedUserId) {
        super(EventType.ALARM, object, subobject, registryDate, spec, dbEvent,severity);
        this.severityChangeTime = severityChangeTime;
        this.clearedUserId = clearedUserId;
    }

    public Timestamp getSeverityChangeTime() {
        return severityChangeTime;
    }

    public void setSeverityChangeTime(Timestamp severityChangeTime) {
        this.severityChangeTime = severityChangeTime;
    }

    public long getClearedUserId() {
        return clearedUserId;
    }

    public void setClearedUserId(long clearedUserId) {
        this.clearedUserId = clearedUserId;
    }

    @Override
    public String toString() {
        return String.format("Alarm event [\n\tregistry time: %s\n\tobject: %s;\n\tsubobject: %s;\n\tspecification: %s;\n\tseverity: %s;\n\tseverity change time: %s\n]",
                getRegistryDate(),
                getObject().getName(),
                getSubobject() == null ? "none" : getSubobject().getName(),
                getSpec().getSpecName(),
                getSeverity().toString(),
                getSeverityChangeTime()
        );
    }
}