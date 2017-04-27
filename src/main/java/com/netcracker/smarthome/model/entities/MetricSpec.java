package com.netcracker.smarthome.model.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "metric_specs", schema = "public", catalog = "smarthome_db")
public class MetricSpec extends Spec {
    private Double maxValue;
    private Double minValue;
    private String metricType;
    private String assignedToObject;
    private Unit unit;
    private List<Metric> metrics;

    public MetricSpec() {
    }

    public MetricSpec(Catalog catalog, Unit unit) {
        super(catalog);
        this.unit = unit;
    }

    public MetricSpec(String specName, Double maxValue, Double minValue, String metricType, String assignedToObject, Unit unit, Catalog catalog) {
        super(specName, catalog);
        this.maxValue = maxValue;
        this.minValue = minValue;
        this.metricType = metricType;
        this.assignedToObject = assignedToObject;
        this.unit = unit;
    }

    @Id
    @Column(name = "spec_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "m_spec_seq")
    @SequenceGenerator(name = "m_spec_seq", sequenceName = "metric_specs_spec_id_seq", allocationSize = 1)
    @Override
    public long getSpecId() {
        return super.getSpecId();
    }


    @Basic
    @Column(name = "spec_name", nullable = false)
    @Override
    public String getSpecName() {
        return super.getSpecName();
    }

    @Basic
    @Column(name = "max_value", nullable = true, precision = 0)
    public Double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Double maxValue) {
        this.maxValue = maxValue;
    }

    @Basic
    @Column(name = "min_value", nullable = true, precision = 0)
    public Double getMinValue() {
        return minValue;
    }

    public void setMinValue(Double minValue) {
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
    @Override
    public Catalog getCatalog() {
        return super.getCatalog();
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