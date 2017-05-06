package com.netcracker.smarthome.business.policy.transform.json.entities;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.io.Serializable;
import java.util.List;

@JsonPropertyOrder({"id", "name", "root_condition", "then_actions", "else_actions"})
public class JsonRule implements Serializable {
    private long id;
    private String name;
    private JsonCondition rootCondition;
    private List<JsonAction> thenActions;
    private List<JsonAction> elseActions;

    public JsonRule() {
    }

    public JsonRule(long id, String name, JsonCondition rootCondition, List<JsonAction> thenActions, List<JsonAction> elseActions) {
        this.id = id;
        this.name = name;
        this.rootCondition = rootCondition;
        this.thenActions = thenActions;
        this.elseActions = elseActions;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonGetter("root_condition")
    public JsonCondition getRootCondition() {
        return rootCondition;
    }

    @JsonSetter("root_condition")
    public void setRootCondition(JsonCondition rootCondition) {
        this.rootCondition = rootCondition;
    }

    @JsonGetter("then_actions")
    public List<JsonAction> getThenActions() {
        return thenActions;
    }

    @JsonSetter("then_actions")
    public void setThenActions(List<JsonAction> thenActions) {
        this.thenActions = thenActions;
    }

    @JsonGetter("else_actions")
    public List<JsonAction> getElseActions() {
        return elseActions;
    }

    @JsonSetter("else_actions")
    public void setElseActions(List<JsonAction> elseActions) {
        this.elseActions = elseActions;
    }
}
