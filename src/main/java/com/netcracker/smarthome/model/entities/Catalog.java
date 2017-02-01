package com.netcracker.smarthome.model.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "catalogs", schema = "public", catalog = "smarthome_db")
public class Catalog implements Serializable {
    private long catalogId;
    private String catalogName;
    private Collection<AlarmSpec> alarmSpecs;
    private Catalog parentCatalog;
    private Collection<Catalog> catalogs;
    private Collection<Policy> policies;

    public Catalog() {
    }

    public Catalog(String catalogName, Catalog parentCatalog) {
        this.catalogName = catalogName;
        this.parentCatalog = parentCatalog;
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
    @Column(name = "catalog_name", nullable = false, length = -1)
    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
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
                .toString();
    }

}
