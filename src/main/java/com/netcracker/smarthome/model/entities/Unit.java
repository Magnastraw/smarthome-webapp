package com.netcracker.smarthome.model.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;

@Entity
@Table(name = "units", schema = "public", catalog = "smarthome_db")
public class Unit implements Serializable {
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "unit_seq")
    @SequenceGenerator(name = "unit_seq", sequenceName = "units_unit_id_seq", allocationSize = 1)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Unit)) return false;

        Unit unit = (Unit) o;

        return new EqualsBuilder()
                .append(getUnitId(), unit.getUnitId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getUnitId())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("unitId", getUnitId())
                .append("unitName", getUnitName())
                .append("baseFactor", getUnitName())
                .append("label", getLabel())
                .append("parentUnit", getParentUnit())
                .toString();
    }
}
