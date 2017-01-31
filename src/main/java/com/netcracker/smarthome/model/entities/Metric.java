package com.netcracker.smarthome.model.entities;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "metrics", schema = "public", catalog = "smarthome_db")
public class Metric {
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
    @SequenceGenerator(name = "metric_seq", sequenceName = "metrics_metric_id_seq")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Metric metric = (Metric) o;

        if (metricId != metric.metricId) return false;
        if (objectId != metric.objectId) return false;
        if (subobjectId != null ? !subobjectId.equals(metric.subobjectId) : metric.subobjectId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (metricId ^ (metricId >>> 32));
        result = 31 * result + (int) (objectId ^ (objectId >>> 32));
        result = 31 * result + (subobjectId != null ? subobjectId.hashCode() : 0);
        return result;
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
}
