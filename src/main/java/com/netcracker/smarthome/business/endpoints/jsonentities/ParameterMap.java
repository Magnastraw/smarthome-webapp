package com.netcracker.smarthome.business.endpoints.jsonentities;

import java.io.Serializable;
import java.util.Map;

public class ParameterMap implements Serializable {
    private Map<String,JsonParameter> parameters;

    public ParameterMap() {}

    public ParameterMap(Map<String,JsonParameter> parameters) {
        this.parameters = parameters;
    }

    public Map<String, JsonParameter> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, JsonParameter> parameters) {
        this.parameters = parameters;
    }
}
