package com.netcracker.smarthome.model.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "metrics", schema = "public", catalog = "smarthome_db")
public class Metric implements Serializable {
    private long metricId;
    private SmartObject object;
    private SmartObject subobject;
    private MetricSpec metricSpec;
    private SmartHome smartHome;
    private List<MetricHistory> metricHistory;
    private List<Notification> notifications;

    public Metric() {
    }

    public Metric(SmartObject object, SmartObject subobject, MetricSpec metricSpec, SmartHome smartHome) {
        this.object = object;
        this.subobject = subobject;
        this.metricSpec = metricSpec;
        this.smartHome = smartHome;
    }

    @Id
    @Column(name = "metric_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "metric_seq")
    @SequenceGenerator(name = "metric_seq", sequenceName = "metrics_metric_id_seq", allocationSize = 1)
    public long getMetricId() {
        return metricId;
    }

    public void setMetricId(long metricId) {
        this.metricId = metricId;
    }

    @ManyToOne
    @JoinColumn(name = "object_id", referencedColumnName = "smart_object_id", nullable = false)
    public SmartObject getObject() {
        return object;
    }

    public void setObject(SmartObject object) {
        this.object = object;
    }

    @ManyToOne
    @JoinColumn(name = "subobject_id", referencedColumnName = "smart_object_id")
    public SmartObject getSubobject() {
        return subobject;
    }

    public void setSubobject(SmartObject subobject) {
        this.subobject = subobject;
    }

    @ManyToOne
    @JoinColumn(name = "spec_id", referencedColumnName = "spec_id", nullable = false)
    public MetricSpec getMetricSpec() {
        return metricSpec;
    }

    public void setMetricSpec(MetricSpec metricSpec) {
        this.metricSpec = metricSpec;
    }

    @ManyToOne
    @JoinColumn(name = "smart_home_id", referencedColumnName = "smart_home_id", nullable = false)
    public SmartHome getSmartHome() {
        return smartHome;
    }

    public void setSmartHome(SmartHome smartHome) {
        this.smartHome = smartHome;
    }

    @OneToMany(mappedBy = "metric")
    public List<MetricHistory> getMetricHistory() {
        return metricHistory;
    }

    public void setMetricHistory(List<MetricHistory> metricHistory) {
        this.metricHistory = metricHistory;
    }

    @OneToMany(mappedBy = "metric")
    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Metric)) return false;

        Metric metric = (Metric) o;

        return new EqualsBuilder()
                .append(getMetricId(), metric.getMetricId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getMetricId())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("metricId", getMetricId())
                .append("object", getObject())
                .append("subobject", getSubobject())
                .append("smartHome", getSmartHome())
                .append("metricSpec", getMetricSpec())
                .toString();
    }
}
