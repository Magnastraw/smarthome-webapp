package com.netcracker.smarthome.model.entities;

import java.io.Serializable;

public class Spec implements Serializable {
    private long specId;
    private String specName;
    private Catalog catalog;

    public Spec() {
    }

    public Spec(Catalog catalog) {
        this.catalog = catalog;
    }

    public Spec(String specName, Catalog catalog) {
        this.specName = specName;
        this.catalog = catalog;
    }

    public long getSpecId() {
        return specId;
    }

    public void setSpecId(long specId) {
        this.specId = specId;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }
}