package com.netcracker.smarthome.business.endpoints.jsonentities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class JsonInventoryObject implements Serializable {
    private long objectId;
    private String objectName;
    private String objectType;
    private long parentId;
    private List<JsonParameter> parameters;

    public JsonInventoryObject() {
        this.parameters = new ArrayList<JsonParameter>();
    }

    public JsonInventoryObject(long objectId, String objectName, String objectType, long parentId, List<JsonParameter> parameters) {
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

    public List<JsonParameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<JsonParameter> parameters) {
        this.parameters = parameters;
    }

    public void addParameter(JsonParameter parameter) {
        this.parameters.add(parameter);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Inventory object:\n");
        sb.append("Id = "+objectId+"\n");
        sb.append("Name = "+objectName+"\n");
        sb.append("Type = "+objectType+"\n");
        sb.append("ParentId = "+parentId+"\n");
        sb.append("Parameters:\n");
        for (JsonParameter p : parameters) {
            sb.append("Name = "+p.getName()+"\n");
            sb.append("Value = "+p.getValue()+"\n");
            sb.append("Type = "+p.getType()+"\n");
        }
        sb.append("********\n");
        return sb.toString();
    }
}
