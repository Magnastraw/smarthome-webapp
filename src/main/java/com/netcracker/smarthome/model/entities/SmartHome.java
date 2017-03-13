package com.netcracker.smarthome.model.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "smart_homes", schema = "public", catalog = "smarthome_db")
public class SmartHome implements Serializable {
    private long smartHomeId;
    private String name;
    private String description;
    private List<Catalog> catalog;
    private List<Event> events;
    private List<HomeParam> homeParams;
    private List<Metric> metrics;
    private List<Notification> notifications;
    private List<SmartObject> objects;
    private User user;

    public SmartHome() {
    }

    public SmartHome(String name, String description, User user) {
        this.name = name;
        this.description = description;
        this.user = user;
    }

    @Id
    @Column(name = "smart_home_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "home_seq")
    @SequenceGenerator(name = "home_seq", sequenceName = "smart_homes_home_id_seq", allocationSize = 1)
    public long getSmartHomeId() {
        return smartHomeId;
    }

    public void setSmartHomeId(long smartHomeId) {
        this.smartHomeId = smartHomeId;
    }

    @Basic
    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "description", nullable = false)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany(mappedBy = "smartHome")
    public List<Catalog> getCatalog() {
        return catalog;
    }

    public void setCatalog(List<Catalog> catalog) {
        this.catalog = catalog;
    }

    @OneToMany(mappedBy = "smartHome")
    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    @OneToMany(mappedBy = "smartHome", cascade = {CascadeType.PERSIST})
    public List<HomeParam> getHomeParams() {
        return homeParams;
    }

    public void setHomeParams(List<HomeParam> homeParams) {
        this.homeParams = homeParams;
    }

    @OneToMany(mappedBy = "smartHome")
    public List<Metric> getMetrics() {
        return metrics;
    }

    public void setMetrics(List<Metric> metrics) {
        this.metrics = metrics;
    }

    @OneToMany(mappedBy = "smartHome")
    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    @OneToMany(mappedBy = "smartHome")
    public List<SmartObject> getObjects() {
        return objects;
    }

    public void setObjects(List<SmartObject> objects) {
        this.objects = objects;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof SmartHome)) return false;

        SmartHome smartHome = (SmartHome) o;

        return new EqualsBuilder()
                .append(getSmartHomeId(), smartHome.getSmartHomeId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getSmartHomeId())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("smartHomeId", getSmartHomeId())
                .append("name", getName())
                .append("description", getDescription())
                .append("user", getUser())
                .toString();
    }
}
