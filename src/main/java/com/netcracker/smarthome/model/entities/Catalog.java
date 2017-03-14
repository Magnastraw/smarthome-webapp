package com.netcracker.smarthome.model.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "catalogs", schema = "public", catalog = "smarthome_db")
public class Catalog implements Serializable {
    private long catalogId;
    private String catalogName;
    private List<AlarmSpec> alarmSpecs;
    private Catalog parentCatalog;
    private List<Catalog> subcatalogs;
    private SmartHome smartHome;
    private List<MetricSpec> metricSpecs;
    private List<SmartObject> objects;
    private List<Policy> policies;
    private List<Assignment> assignments;

    public Catalog() {
    }

    public Catalog(String catalogName, Catalog parentCatalog, SmartHome smartHome) {
        this.catalogName = catalogName;
        this.parentCatalog = parentCatalog;
        this.smartHome = smartHome;
    }

    @Id
    @Column(name = "catalog_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "catalog_seq")
    @SequenceGenerator(name = "catalog_seq", sequenceName = "catalogs_catalog_id_seq", allocationSize = 1)
    public long getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(long catalogId) {
        this.catalogId = catalogId;
    }

    @Basic
    @Column(name = "catalog_name", nullable = false)
    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    @OneToMany(mappedBy = "catalog")
    public List<AlarmSpec> getAlarmSpecs() {
        return alarmSpecs;
    }

    public void setAlarmSpecs(List<AlarmSpec> alarmSpecs) {
        this.alarmSpecs = alarmSpecs;
    }

    @ManyToOne
    @JoinColumn(name = "parent_catalog_id", referencedColumnName = "catalog_id")
    public Catalog getParentCatalog() {
        return parentCatalog;
    }

    public void setParentCatalog(Catalog parentCatalog) {
        this.parentCatalog = parentCatalog;
    }

    @OneToMany(mappedBy = "parentCatalog", cascade = CascadeType.ALL)
    public List<Catalog> getSubcatalogs() {
        return subcatalogs;
    }

    public void setSubcatalogs(List<Catalog> subcatalogs) {
        this.subcatalogs = subcatalogs;
    }

    @ManyToOne
    @JoinColumn(name = "smart_home_id", referencedColumnName = "smart_home_id", nullable = false)
    public SmartHome getSmartHome() {
        return smartHome;
    }

    public void setSmartHome(SmartHome smartHome) {
        this.smartHome = smartHome;
    }

    @OneToMany(mappedBy = "catalog")
    public List<MetricSpec> getMetricSpecs() {
        return metricSpecs;
    }

    public void setMetricSpecs(List<MetricSpec> metricSpecs) {
        this.metricSpecs = metricSpecs;
    }

    @OneToMany(mappedBy = "catalog", cascade = CascadeType.ALL)
    public List<SmartObject> getObjects() {
        return objects;
    }

    public void setObjects(List<SmartObject> objects) {
        this.objects = objects;
    }

    @OneToMany(mappedBy = "catalog", cascade = CascadeType.ALL)
    public List<Policy> getPolicies() {
        return policies;
    }

    public void setPolicies(List<Policy> policies) {
        this.policies = policies;
    }

    @OneToMany(mappedBy = "catalog", cascade = CascadeType.ALL)
    public List<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Catalog)) return false;

        Catalog catalog = (Catalog) o;

        return new EqualsBuilder()
                .append(getCatalogId(), catalog.getCatalogId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getCatalogId())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("catalogId", getCatalogId())
                .append("catalogName", getCatalogName())
                .append("parentCatalog", getParentCatalog())
                .append("smartHome", getSmartHome())
                .toString();
    }
}
