package com.netcracker.smarthome.business.policy.events;

import com.netcracker.smarthome.model.entities.AlarmSpec;
import com.netcracker.smarthome.model.entities.SmartObject;
import com.netcracker.smarthome.model.enums.AlarmSeverity;

import java.sql.Timestamp;

public class AlarmEvent extends EventEvent {
    private Timestamp severityChangeTime;
    private long clearedUserId;

    public AlarmEvent() {
    }

    public AlarmEvent(Event causalEvent, AlarmSpec spec, AlarmSeverity severity, Timestamp severityChangeTime, long clearedUserId) {
        super(causalEvent.getType(), causalEvent.getObject(), causalEvent.getSubobject(), causalEvent.getRegistryDate(), spec, causalEvent.getDbEventId(), severity);
        this.severityChangeTime = severityChangeTime;
        this.clearedUserId = clearedUserId;
    }

    public AlarmEvent(EventType type, SmartObject object, SmartObject subobject, Timestamp registryDate, AlarmSpec spec, long dbEventId, AlarmSeverity severity, Timestamp severityChangeTime, long clearedUserId) {
        super(type, object, subobject, registryDate, spec, dbEventId, severity);
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

    public void setClearedUser(long clearedUserId) {
        this.clearedUserId = clearedUserId;
    }
}
