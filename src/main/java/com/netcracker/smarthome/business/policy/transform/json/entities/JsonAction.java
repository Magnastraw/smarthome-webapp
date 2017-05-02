package com.netcracker.smarthome.business.policy.transform.json.entities;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.io.Serializable;

public class JsonAction implements Serializable {
    private long id;
    private long order;
    private String actionClass;
    private JsonParams params;

    public JsonAction() {
    }

    public JsonAction(long id, long order, String actionClass, JsonParams params) {
        this.id = id;
        this.order = order;
        this.actionClass = actionClass;
        this.params = params;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOrder() {
        return order;
    }

    public void setOrder(long order) {
        this.order = order;
    }

    @JsonGetter("class")
    public String getActionClass() {
        return actionClass;
    }

    @JsonSetter("class")
    public void setActionClass(String actionClass) {
        this.actionClass = actionClass;
    }

    public JsonParams getParams() {
        return params;
    }

    public void setParams(JsonParams params) {
        this.params = params;
    }
}
