package com.netcracker.smarthome.model.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "metrics", schema = "public", catalog = "smarthome_db")
public class Metric implements Serializable {
    private long metricId;
    private long objectId;
    private Long subobjectId;
    private User user;
    private MetricSpec metricSpec;
    private Collection<MetricHistory> metricHistories;
    private Collection<Notification> notifications;

    public Metric() {
    }

    public Metric(long objectId, Long subobjectId, User user, MetricSpec metricSpec) {
        this.objectId = objectId;
        this.subobjectId = subobjectId;
        this.user = user;
        this.metricSpec = metricSpec;
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

    @Basic
    @Column(name = "object_id", nullable = false)
    public long getObjectId() {
        return objectId;
    }

    public void setObjectId(long objectId) {
        this.objectId = objectId;
    }

    @Basic
    @Column(name = "subobject_id", nullable = true)
    public Long getSubobjectId() {
        return subobjectId;
    }

    public void setSubobjectId(Long subobjectId) {
        this.subobjectId = subobjectId;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name = "spec_id", referencedColumnName = "spec_id", nullable = false)
    public MetricSpec getMetricSpec() {
        return metricSpec;
    }

    public void setMetricSpec(MetricSpec metricSpec) {
        this.metricSpec = metricSpec;
    }

    @OneToMany(mappedBy = "metric")
    public Collection<MetricHistory> getMetricHistories() {
        return metricHistories;
    }

    public void setMetricHistories(Collection<MetricHistory> metricHistories) {
        this.metricHistories = metricHistories;
    }

    @OneToMany(mappedBy = "metric")
    public Collection<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(Collection<Notification> notifications) {
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
                .append("objectId", getObjectId())
                .append("subobjectId", getSubobjectId())
                .append("user", getUser())
                .append("metricSpec", getMetricSpec())
                .toString();
    }

}
