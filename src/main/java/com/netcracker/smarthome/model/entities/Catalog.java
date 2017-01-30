package com.netcracker.smarthome.model.entities;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "catalogs", schema = "public", catalog = "smarthome_db")
public class Catalog {
    private long catalogId;
    private String catalogName;
    private Collection<AlarmSpec> alarmSpecs;
    private Catalog parentCatalog;
    private Collection<Catalog> catalogs;
    private Collection<Policy> policies;

    @Id
    @Column(name = "catalog_id", nullable = false)
    public long getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(long catalogId) {
        this.catalogId = catalogId;
    }

    @Basic
    @Column(name = "catalog_name", nullable = false, length = -1)
    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Catalog catalog = (Catalog) o;

        if (catalogId != catalog.catalogId) return false;
        if (catalogName != null ? !catalogName.equals(catalog.catalogName) : catalog.catalogName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (catalogId ^ (catalogId >>> 32));
        result = 31 * result + (catalogName != null ? catalogName.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "catalog")
    public Collection<AlarmSpec> getAlarmSpecs() {
        return alarmSpecs;
    }

    public void setAlarmSpecs(Collection<AlarmSpec> alarmSpecs) {
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

    @OneToMany(mappedBy = "parentCatalog")
    public Collection<Catalog> getCatalogs() {
        return catalogs;
    }

    public void setCatalogs(Collection<Catalog> catalogs) {
        this.catalogs = catalogs;
    }

    @OneToMany(mappedBy = "catalog")
    public Collection<Policy> getPolicies() {
        return policies;
    }

    public void setPolicies(Collection<Policy> policies) {
        this.policies = policies;
    }
}
