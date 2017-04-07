package com.netcracker.smarthome.business.endpoints.jsonentities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.sql.Timestamp;

public class JsonEvent implements Serializable {
    private long objectId;
    private long subobjectId;
    private String eventType;
    private long severity;
    private Timestamp registryDate;
    private String eventParameters;

    public JsonEvent() {}

    public JsonEvent(long objectId, long subobjectId, long severity, Timestamp registryDate, String eventParameters, String eventType) {
        this.objectId = objectId;
        this.subobjectId = subobjectId;
        this.severity = severity;
        this.registryDate = registryDate;
        this.eventParameters = eventParameters;
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

    public String getEventParameters() {
        return eventParameters;
    }

    public void setEventParameters(String eventParameters) {
        this.eventParameters = eventParameters;
    }

    public String getEventType() {
        return eventType;
    }

}
