package com.netcracker.smarthome.business.endpoints.jsonentities;

import java.io.Serializable;
import java.sql.Timestamp;

public class JsonAlarm implements Serializable {
    private long objectId;
    private long subobjectId;
    private long severity;
    private Timestamp registryDate;
    private String alarmParameters;
    private long specId;
    private String eventType;

    public JsonAlarm() {}

    public JsonAlarm(long objectId, long subobjectId, long severity, Timestamp registryDate, String alarmParameters, long specId, String eventType) {
        this.objectId = objectId;
        this.subobjectId = subobjectId;
        this.severity = severity;
        this.registryDate = registryDate;
        this.alarmParameters = alarmParameters;
        this.specId = specId;
        this.eventType = eventType;
    }

    public long getObjectId() {
        return objectId;
    }

    public void setObjectId(long objectId) {
        this.objectId = objectId;
    }

    public long getSubobjectId() {
        return subobjectId;
    }

    public void setSubobjectId(long subobjectId) {
        this.subobjectId = subobjectId;
    }

    public long getSeverity() {
        return severity;
    }

    public void setSeverity(long severity) {
        this.severity = severity;
    }

    public Timestamp getRegistryDate() {
        return registryDate;
    }

    public void setRegistryDate(Timestamp registryDate) {
        this.registryDate = registryDate;
    }

    public String getAlarmParameters() {
        return alarmParameters;
    }

    public void setAlarmParameters(String alarmParameters) {
        this.alarmParameters = alarmParameters;
    }

    public long getSpecId() {
        return specId;
    }

    public void setSpecId(long specId) {
        this.specId = specId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
}
