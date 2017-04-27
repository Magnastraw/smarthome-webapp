package com.netcracker.smarthome.business.policy.events;

import com.netcracker.smarthome.model.entities.AlarmSpec;
import com.netcracker.smarthome.model.entities.Event;
import com.netcracker.smarthome.model.entities.SmartObject;
import com.netcracker.smarthome.model.enums.AlarmSeverity;

import java.sql.Timestamp;

public class AlarmEvent extends PolicyEvent {
    private AlarmSeverity severity;
    private Timestamp severityChangeTime;
    private long clearedUserId;

    public AlarmEvent() {
    }

    public AlarmEvent(PolicyEvent causalEvent, AlarmSpec spec, AlarmSeverity severity, Timestamp severityChangeTime, long clearedUserId) {
        super(EventType.ALARM, causalEvent.getObject(), causalEvent.getSubobject(), causalEvent.getRegistryDate(), spec, causalEvent.getDbEvent());
        this.severity = severity;
        this.severityChangeTime = severityChangeTime;
        this.clearedUserId = clearedUserId;
    }

    public AlarmEvent(SmartObject object, SmartObject subobject, Timestamp registryDate, AlarmSpec spec, Event dbEvent, AlarmSeverity severity, Timestamp severityChangeTime, long clearedUserId) {
        super(EventType.ALARM, object, subobject, registryDate, spec, dbEvent);
        this.severityChangeTime = severityChangeTime;
        this.clearedUserId = clearedUserId;
    }

    public AlarmSeverity getSeverity() {
        return severity;
    }

    public void setSeverity(AlarmSeverity severity) {
        this.severity = severity;
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
}