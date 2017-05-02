package com.netcracker.smarthome.business.endpoints.jsonentities;

import java.util.Map;

public class HomeTask {
    private String action;
    private Map<String,JsonParameter> parameters;

    public HomeTask() {}

    public HomeTask(String action) {
        this.action = action;
    }

    public HomeTask(String action, Map<String,JsonParameter> parameters) {
        this.action = action;
        this.parameters = parameters;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Map<String, JsonParameter> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, JsonParameter> parameters) {
        this.parameters = parameters;
    }

    public void addParameter(String parameterName, JsonParameter parameter) {
        this.parameters.put(parameterName, parameter);
    }
}
