package com.netcracker.smarthome.model.entities;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Collection;

@Entity
@Table(name = "metric_spec", schema = "public", catalog = "smarthome_db")
public class MetricSpec {
    private long specId;
    private String specName;
    private BigInteger maxValue;
    private BigInteger minValue;
    private String metricType;
    private String assignedToObject;
    private Unit unit;
    private Collection<Metric> metrics;

    @Id
    @Column(name = "spec_id", nullable = false)
    public long getSpecId() {
        return specId;
    }

    public void setSpecId(long specId) {
        this.specId = specId;
    }

    @Basic
    @Column(name = "spec_name", nullable = false, length = -1)
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
    @Column(name = "metric_type", nullable = false, length = -1)
    public String getMetricType() {
        return metricType;
    }

    public void setMetricType(String metricType) {
        this.metricType = metricType;
    }

    @Basic
    @Column(name = "assigned_to_object", nullable = true, length = -1)
    public String getAssignedToObject() {
        return assignedToObject;
    }

    public void setAssignedToObject(String assignedToObject) {
        this.assignedToObject = assignedToObject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MetricSpec that = (MetricSpec) o;

        if (specId != that.specId) return false;
        if (specName != null ? !specName.equals(that.specName) : that.specName != null) return false;
        if (maxValue != null ? !maxValue.equals(that.maxValue) : that.maxValue != null) return false;
        if (minValue != null ? !minValue.equals(that.minValue) : that.minValue != null) return false;
        if (metricType != null ? !metricType.equals(that.metricType) : that.metricType != null) return false;
        if (assignedToObject != null ? !assignedToObject.equals(that.assignedToObject) : that.assignedToObject != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (specId ^ (specId >>> 32));
        result = 31 * result + (specName != null ? specName.hashCode() : 0);
        result = 31 * result + (maxValue != null ? maxValue.hashCode() : 0);
        result = 31 * result + (minValue != null ? minValue.hashCode() : 0);
        result = 31 * result + (metricType != null ? metricType.hashCode() : 0);
        result = 31 * result + (assignedToObject != null ? assignedToObject.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "unit_id", referencedColumnName = "unit_id", nullable = false)
    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    @OneToMany(mappedBy = "metricSpec")
    public Collection<Metric> getMetrics() {
        return metrics;
    }

    public void setMetrics(Collection<Metric> metrics) {
        this.metrics = metrics;
    }
}
