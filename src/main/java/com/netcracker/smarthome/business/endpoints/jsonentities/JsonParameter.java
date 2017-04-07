package com.netcracker.smarthome.business.endpoints.jsonentities;

import java.io.Serializable;

public class JsonParameter implements Serializable {
    private String name;
    private String value;
    private String type;

    public JsonParameter() {}

    public JsonParameter(String name) {
        this.name = name;
    }

    public JsonParameter(String name, String value, String type) {
        this.name = name;
        this.value = value;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
