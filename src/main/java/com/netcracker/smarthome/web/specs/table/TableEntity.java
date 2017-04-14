package com.netcracker.smarthome.web.specs.table;

import com.netcracker.smarthome.model.entities.AlarmSpec;
import com.netcracker.smarthome.model.entities.Catalog;
import com.netcracker.smarthome.model.entities.MetricSpec;
import java.io.Serializable;

public class TableEntity implements Serializable {
    private Catalog catalog;
    private MetricSpec metricSpec;
    private AlarmSpec alarmSpec;
    private String name;
    private boolean typeCatalog;

    public TableEntity(Catalog catalog, MetricSpec metricSpec, String name, boolean typeCatalog) {
        this.catalog = catalog;
        this.metricSpec = metricSpec;
        this.alarmSpec = new AlarmSpec();
        this.name = name;
        this.typeCatalog = true;
    }

    public TableEntity(Catalog catalog, String name, boolean typeCatalog) {
        this.catalog = catalog;
        this.metricSpec = new MetricSpec();
        this.alarmSpec = new AlarmSpec();
        this.name = name;
        this.typeCatalog = typeCatalog;
    }

    public TableEntity(MetricSpec metricSpec, String name, boolean typeCatalog) {
        this.catalog = new Catalog();
        this.alarmSpec = new AlarmSpec();
        this.metricSpec = metricSpec;
        this.name = name;
        this.typeCatalog = typeCatalog;
    }

    public TableEntity(AlarmSpec alarmSpec, String name, boolean typeCatalog) {
        this.catalog = new Catalog();
        this.metricSpec = new MetricSpec();
        this.alarmSpec = alarmSpec;
        this.name = name;
        this.typeCatalog = typeCatalog;
    }

    public TableEntity(Catalog catalog, AlarmSpec alarmSpec, String name, boolean typeCatalog) {
        this.catalog = catalog;
        this.alarmSpec = alarmSpec;
        this.name = name;
        this.typeCatalog = true;
        this.metricSpec = new MetricSpec();
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    public MetricSpec getMetricSpec() {
        return metricSpec;
    }

    public void setMetricSpec(MetricSpec metricSpec) {
        this.metricSpec = metricSpec;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isTypeCatalog() {
        return typeCatalog;
    }

    public void setTypeCatalog(boolean typeCatalog) {
        this.typeCatalog = typeCatalog;
    }

    public AlarmSpec getAlarmSpec() {
        return alarmSpec;
    }

    public void setAlarmSpec(AlarmSpec alarmSpec) {
        this.alarmSpec = alarmSpec;
    }
}