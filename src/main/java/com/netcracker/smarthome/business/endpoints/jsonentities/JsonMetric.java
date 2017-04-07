package com.netcracker.smarthome.business.endpoints.jsonentities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.sql.Timestamp;

public class JsonMetric implements Serializable {
    private long objectId;
    private long subobjectId;
    private Timestamp registryDate;
    private double value;
    private long specId;
    @JsonIgnore
    private long smartHomeId;

    public JsonMetric() {}

    public JsonMetric(long objectId, long subobjectId, Timestamp registryDate, double value, long specId) {
        this.objectId = objectId;
        this.subobjectId = subobjectId;
        this.registryDate = registryDate;
        this.value = value;
        this.specId = specId;
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

    public Timestamp getRegistryDate() {
        return registryDate;
    }

    public void setRegistryDate(Timestamp registryDate) {
        this.registryDate = registryDate;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public long getSpecId() {
        return specId;
    }

    public void setSpecId(long specId) {
        this.specId = specId;
    }

    public long getSmartHomeId() {
        return smartHomeId;
    }

    public void setSmartHomeId(long smartHomeId) {
        this.smartHomeId = smartHomeId;
    }
}
