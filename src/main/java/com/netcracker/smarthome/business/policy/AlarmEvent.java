package com.netcracker.smarthome.business.policy;

import com.netcracker.smarthome.model.entities.AlarmSpec;
import com.netcracker.smarthome.model.entities.SmartObject;
import com.netcracker.smarthome.model.entities.User;

import java.util.Date;

public class AlarmEvent extends EventEvent {
    private Date severityChangeTime;
    private User clearedUser;

    public AlarmEvent() {
    }

    public AlarmEvent(EventType type, SmartObject object, SmartObject subobject, Date registryDate, AlarmSpec spec, int severity, Date severityChangeTime, User clearedUser) {
        super(type, object, subobject, registryDate, spec, severity);
        this.severityChangeTime = severityChangeTime;
        this.clearedUser = clearedUser;
    }

    public Date getSeverityChangeTime() {
        return severityChangeTime;
    }

    public void setSeverityChangeTime(Date severityChangeTime) {
        this.severityChangeTime = severityChangeTime;
    }

    public User getClearedUser() {
        return clearedUser;
    }

    public void setClearedUser(User clearedUser) {
        this.clearedUser = clearedUser;
    }
}
