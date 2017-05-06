package com.netcracker.smarthome.business.policy.transform.json.entities;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.netcracker.smarthome.model.enums.BooleanOperator;

import java.io.Serializable;
import java.util.List;

@JsonPropertyOrder({"id", "operator", "class", "params", "children"})
public class JsonCondition implements Serializable {
    private long id;
    private BooleanOperator operator;
    private String conditionClass;
    private JsonParams params;
    private List<JsonCondition> children;

    public JsonCondition() {
    }

    public JsonCondition(long id, BooleanOperator operator, String conditionClass, JsonParams params, List<JsonCondition> children) {
        this.id = id;
        this.operator = operator;
        this.conditionClass = conditionClass;
        this.params = params;
        this.children = children;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BooleanOperator getOperator() {
        return operator;
    }

    public void setOperator(BooleanOperator operator) {
        this.operator = operator;
    }

    @JsonGetter("class")
    public String getConditionClass() {
        return conditionClass;
    }

    @JsonSetter("class")
    public void setConditionClass(String conditionClass) {
        this.conditionClass = conditionClass;
    }

    public JsonParams getParams() {
        return params;
    }

    public void setParams(JsonParams params) {
        this.params = params;
    }

    public List<JsonCondition> getChildren() {
        return children;
    }

    public void setChildren(List<JsonCondition> children) {
        this.children = children;
    }
}
