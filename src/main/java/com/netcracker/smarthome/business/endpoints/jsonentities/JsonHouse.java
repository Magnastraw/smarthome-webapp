package com.netcracker.smarthome.business.endpoints.jsonentities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class JsonHouse implements Serializable {
    private long houseId;
    private List<JsonParameter> parameters;

    public JsonHouse() {
        this.parameters = new ArrayList<JsonParameter>();
    }

    public JsonHouse(long houseId, List<JsonParameter> parameters) {
        this.houseId = houseId;
        this.parameters = parameters;
    }

    public long getHouseId() {
        return houseId;
    }

    public void setHouseId(long houseId) {
        this.houseId = houseId;
    }

    public List<JsonParameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<JsonParameter> parameters) {
        this.parameters = parameters;
    }
}
