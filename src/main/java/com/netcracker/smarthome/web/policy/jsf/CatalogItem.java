package com.netcracker.smarthome.web.policy.jsf;

import com.netcracker.smarthome.model.entities.Catalog;
import com.netcracker.smarthome.model.enums.PolicyStatus;

public class CatalogItem extends PoliciesTableItem {
    private Catalog catalog;

    public CatalogItem(Catalog catalog) {
        this.catalog = catalog;
    }

    public String getName() {
        return catalog.getCatalogName();
    }

    public void setName(String name) {
        catalog.setCatalogName(name);
    }

    public String getDescription() {
        return "";
    }

    public void setDescription(String description) {
    }

    public PolicyStatus getStatus() {
        return null;
    }

    public void setStatus(PolicyStatus status) {
    }

    public Catalog getParentCatalog() {
        return catalog.getParentCatalog();
    }

    public void setParentCatalog(Catalog catalog) {
        catalog.setParentCatalog(catalog);
    }

    public boolean isCatalog() {
        return true;
    }

    public Catalog getItem() {
        return catalog;
    }

    public void setItem(Object item) {
        catalog = (Catalog) item;
    }
}
