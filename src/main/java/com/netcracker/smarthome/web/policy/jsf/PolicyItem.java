package com.netcracker.smarthome.web.policy.jsf;

import com.netcracker.smarthome.model.entities.Catalog;
import com.netcracker.smarthome.model.entities.Policy;
import com.netcracker.smarthome.model.enums.PolicyStatus;

public class PolicyItem extends PoliciesTableItem {
    private Policy policy;

    public PolicyItem(Policy policy) {
        this.policy = policy;
    }

    public String getName() {
        return policy.getName();
    }

    public void setName(String name) {
        policy.setName(name);
    }

    public String getDescription() {
        return policy.getDescription();
    }

    public void setDescription(String description) {
        policy.setDescription(description);
    }

    public PolicyStatus getStatus() {
        return policy.getStatus();
    }

    public void setStatus(PolicyStatus status) {
        policy.setStatus(status);
    }

    public Catalog getParentCatalog() {
        return policy.getCatalog();
    }

    public void setParentCatalog(Catalog catalog) {
        policy.setCatalog(catalog);
    }

    public boolean isCatalog() {
        return false;
    }

    public Policy getItem() {
        return policy;
    }

    public void setItem(Object item) {
        policy = (Policy) item;
    }
}
