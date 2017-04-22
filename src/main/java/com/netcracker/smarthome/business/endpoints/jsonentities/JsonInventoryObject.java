package com.netcracker.smarthome.business.endpoints.jsonentities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class JsonInventoryObject implements Serializable {
    private long objectId;
    private String objectName;
    private String objectType;
    private long parentId;
    private Map<String,JsonParameter> parameters;
    @JsonIgnore
    private long smartHomeId;

    public JsonInventoryObject() {
        this.parameters = new LinkedHashMap<String, JsonParameter>();
    }

    public JsonInventoryObject(long objectId, String objectName, String objectType, long parentId, Map<String,JsonParameter> parameters) {
        this.objectId = objectId;
        this.objectName = objectName;
        this.objectType = objectType;
        this.parentId = parentId;
        this.parameters = parameters;
    }

    public long getObjectId() {
        return objectId;
    }

    public void setObjectId(long objectId) {
        this.objectId = objectId;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public Map<String,JsonParameter> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String,JsonParameter> parameters) {
        this.parameters = parameters;
    }

    public void addParameter(String paramName, JsonParameter parameter) {
        this.parameters.put(paramName, parameter);
    }

    public long getSmartHomeId() {
        return smartHomeId;
    }

    public void setSmartHomeId(long smartHomeId) {
        this.smartHomeId = smartHomeId;
    }
}
