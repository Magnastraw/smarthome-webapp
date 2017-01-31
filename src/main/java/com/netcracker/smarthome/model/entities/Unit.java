package com.netcracker.smarthome.model.entities;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Collection;

@Entity
@Table(name = "units", schema = "public", catalog = "smarthome_db")
public class Unit {
    private long unitId;
    private String unitName;
    private BigInteger baseFactor;
    private String label;
    private Collection<MetricSpec> metricSpecs;
    private Unit parentUnit;
    private Collection<Unit> subUnits;

    public Unit() {
    }

    public Unit(String unitName, BigInteger baseFactor, String label, Unit parentUnit) {
        this.unitName = unitName;
        this.baseFactor = baseFactor;
        this.label = label;
        this.parentUnit = parentUnit;
    }

    @Id
    @Column(name = "unit_id", nullable = false)
    public long getUnitId() {
        return unitId;
    }

    public void setUnitId(long unitId) {
        this.unitId = unitId;
    }

    @Basic
    @Column(name = "unit_name", nullable = false, length = -1)
    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    @Basic
    @Column(name = "base_factor", nullable = false, precision = 0)
    public BigInteger getBaseFactor() {
        return baseFactor;
    }

    public void setBaseFactor(BigInteger baseFactor) {
        this.baseFactor = baseFactor;
    }

    @Basic
    @Column(name = "label", nullable = false, length = -1)
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Unit unit = (Unit) o;

        if (unitId != unit.unitId) return false;
        if (unitName != null ? !unitName.equals(unit.unitName) : unit.unitName != null) return false;
        if (baseFactor != null ? !baseFactor.equals(unit.baseFactor) : unit.baseFactor != null) return false;
        if (label != null ? !label.equals(unit.label) : unit.label != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (unitId ^ (unitId >>> 32));
        result = 31 * result + (unitName != null ? unitName.hashCode() : 0);
        result = 31 * result + (baseFactor != null ? baseFactor.hashCode() : 0);
        result = 31 * result + (label != null ? label.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "unit")
    public Collection<MetricSpec> getMetricSpecs() {
        return metricSpecs;
    }

    public void setMetricSpecs(Collection<MetricSpec> metricSpecs) {
        this.metricSpecs = metricSpecs;
    }

    @ManyToOne
    @JoinColumn(name = "parent_unit_id", referencedColumnName = "unit_id")
    public Unit getParentUnit() {
        return parentUnit;
    }

    public void setParentUnit(Unit parentUnit) {
        this.parentUnit = parentUnit;
    }

    @OneToMany(mappedBy = "parentUnit")
    public Collection<Unit> getSubUnits() {
        return subUnits;
    }

    public void setSubUnits(Collection<Unit> subUnits) {
        this.subUnits = subUnits;
    }
}
