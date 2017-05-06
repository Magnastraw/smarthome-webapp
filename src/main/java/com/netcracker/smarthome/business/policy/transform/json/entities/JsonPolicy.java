package com.netcracker.smarthome.business.policy.transform.json.entities;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.netcracker.smarthome.model.enums.PolicyStatus;

import java.io.Serializable;
import java.util.List;

@JsonPropertyOrder({"id", "name", "assigned_objects", "rules"})
public class JsonPolicy implements Serializable {
    private long id;
    private String name;
    private List<Long> assignedObjects;
    private List<JsonRule> rules;

    public JsonPolicy() {
    }

    public JsonPolicy(long id, String name, List<Long> assignedObjects, List<JsonRule> rules) {
        this.id = id;
        this.name = name;
        this.assignedObjects = assignedObjects;
        this.rules = rules;
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

    @JsonGetter("assigned_objects")
    public List<Long> getAssignedObjects() {
        return assignedObjects;
    }

    @JsonSetter("assigned_objects")
    public void setAssignedObjects(List<Long> assignedObjects) {
        this.assignedObjects = assignedObjects;
    }

    public List<JsonRule> getRules() {
        return rules;
    }

    public void setRules(List<JsonRule> rules) {
        this.rules = rules;
    }
}
