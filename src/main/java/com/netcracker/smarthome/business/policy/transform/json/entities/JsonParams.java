package com.netcracker.smarthome.business.policy.transform.json.entities;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class JsonParams implements Serializable {
    private Map<String, Object> params;

    public JsonParams() {
        params = new HashMap<>();
    }

    public JsonParams(Map<String, Object> params) {
        this.params = params;
    }

    @JsonAnyGetter
    public Map<String, Object> getParams() {
        return params;
    }

    @JsonAnySetter
    public void setParam(String name, Object value) {
        this.params.put(name, value);
    }
}
