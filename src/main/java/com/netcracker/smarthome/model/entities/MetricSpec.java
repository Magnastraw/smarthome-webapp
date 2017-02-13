package com.netcracker.smarthome.model.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

@Entity
@Table(name = "metric_specs", schema = "public", catalog = "smarthome_db")
public class MetricSpec implements Serializable {
    private long specId;
    private String specName;
    private BigInteger maxValue;
    private BigInteger minValue;
    private String metricType;
    private String assignedToObject;
    private Unit unit;
    private Catalog catalog;
    private List<Metric> metrics;

    public MetricSpec() {
    }

    public MetricSpec(String specName, BigInteger maxValue, BigInteger minValue, String metricType, String assignedToObject, Unit unit, Catalog catalog) {
        this.specName = specName;
        this.maxValue = maxValue;
        this.minValue = minValue;
        this.metricType = metricType;
        this.assignedToObject = assignedToObject;
        this.unit = unit;
        this.catalog = catalog;
    }

    @Id
    @Column(name = "spec_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "m_spec_seq")
    @SequenceGenerator(name = "m_spec_seq", sequenceName = "metric_specs_spec_id_seq", allocationSize = 1)
    public long getSpecId() {
        return specId;
    }

    public void setSpecId(long specId) {
        this.specId = specId;
    }

    @Basic
    @Column(name = "spec_name", nullable = false)
    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    @Basic
    @Column(name = "max_value", nullable = true, precision = 0)
    public BigInteger getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(BigInteger maxValue) {
        this.maxValue = maxValue;
    }

    @Basic
    @Column(name = "min_value", nullable = true, precision = 0)
    public BigInteger getMinValue() {
        return minValue;
    }

    public void setMinValue(BigInteger minValue) {
        this.minValue = minValue;
    }

    @Basic
    @Column(name = "metric_type", nullable = false)
    public String getMetricType() {
        return metricType;
    }

    public void setMetricType(String metricType) {
        this.metricType = metricType;
    }

    @Basic
    @Column(name = "assigned_to_object", nullable = true)
    public String getAssignedToObject() {
        return assignedToObject;
    }

    public void setAssignedToObject(String assignedToObject) {
        this.assignedToObject = assignedToObject;
    }

    @ManyToOne
    @JoinColumn(name = "unit_id", referencedColumnName = "unit_id")
    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    @ManyToOne
    @JoinColumn(name = "catalog_id", referencedColumnName = "catalog_id", nullable = false)
    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    @OneToMany(mappedBy = "metricSpec")
    public List<Metric> getMetrics() {
        return metrics;
    }

    public void setMetrics(List<Metric> metrics) {
        this.metrics = metrics;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof MetricSpec)) return false;

        MetricSpec that = (MetricSpec) o;

        return new EqualsBuilder()
                .append(getSpecId(), that.getSpecId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getSpecId())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("specId", getSpecId())
                .append("specName", getSpecName())
                .append("maxValue", getMaxValue())
                .append("minValue", getMinValue())
                .append("metricType", getMetricType())
                .append("assignedToObject", getAssignedToObject())
                .append("unit", getUnit())
                .toString();
    }
}