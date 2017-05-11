package com.netcracker.smarthome.web.policy;

import com.netcracker.smarthome.model.entities.Catalog;
import com.netcracker.smarthome.model.enums.PolicyStatus;

public abstract class PoliciesTableItem {
    public abstract String getName();
    public abstract void setName(String name);
    public abstract String getDescription();
    public abstract void setDescription(String description);
    public abstract PolicyStatus getStatus();
    public abstract void setStatus(PolicyStatus status);
    public abstract Catalog getParentCatalog();
    public abstract void setParentCatalog(Catalog catalog);
    public abstract boolean isCatalog();
    public abstract Object getItem();
    public abstract void setItem(Object item);
}
