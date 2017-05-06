package com.netcracker.smarthome.business.endpoints.jsonentities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.sql.Timestamp;

public class JsonEvent implements Serializable {
    private long objectId;
    private long subobjectId;
    private String severity;
    @JsonFormat(timezone = "Europe/Moscow")
    private Timestamp registryDate;
    private String eventParameters;
    @JsonIgnore
    private long smartHomeId;

    public JsonEvent() {}

    public JsonEvent(long objectId, long subobjectId, String severity, Timestamp registryDate, String eventParameterse) {
        this.objectId = objectId;
        this.subobjectId = subobjectId;
        this.severity = severity;
        this.registryDate = registryDate;
        this.eventParameters = eventParameters;
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

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
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

    public long getSmartHomeId() {
        return smartHomeId;
    }

    public void setSmartHomeId(long smartHomeId) {
        this.smartHomeId = smartHomeId;
    }
}
