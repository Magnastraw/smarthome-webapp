package com.netcracker.smarthome.business.policy.events;

import com.netcracker.smarthome.model.entities.AlarmSpec;
import com.netcracker.smarthome.model.entities.SmartObject;
import com.netcracker.smarthome.model.entities.User;

import java.sql.Timestamp;
import java.util.Date;

public class AlarmEvent extends EventEvent {
    private Timestamp severityChangeTime;
    private long clearedUserId;
    private AlarmSpec alarmSpec;

    public AlarmEvent() {
    }

    public AlarmEvent(EventType type, SmartObject object, SmartObject subobject, Timestamp registryDate, AlarmSpec spec, int severity, Timestamp severityChangeTime, long clearedUserId) {
        super(type, object, subobject, registryDate, null, severity);
        this.alarmSpec = spec;
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

    public AlarmSpec getAlarmSpec() {
        return alarmSpec;
    }

    public void setAlarmSpec(AlarmSpec alarmSpec) {
        this.alarmSpec = alarmSpec;
    }
    }
