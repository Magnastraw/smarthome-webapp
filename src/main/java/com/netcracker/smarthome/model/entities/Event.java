package com.netcracker.smarthome.model.entities;

import com.netcracker.smarthome.model.interfaces.NotificationObject;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "events", schema = "public", catalog = "smarthome_db")
public class Event implements Serializable, NotificationObject {
    private long eventId;
    private Long eventType;
    private List<Alarm> alarms;
    private SmartObject object;
    private SmartObject subobject;
    private SmartHome smartHome;
    private List<EventHistory> eventHistory;
    private List<Notification> notifications;

    public Event() {
    }

    public Event(Long eventType, SmartObject object, SmartObject subobject, SmartHome smartHome) {
        this.eventType = eventType;
        this.object = object;
        this.subobject = subobject;
        this.smartHome = smartHome;
    }

    @Id
    @Column(name = "event_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_seq")
    @SequenceGenerator(name = "event_seq", sequenceName = "events_event_id_seq", allocationSize = 1)
    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    @Basic
    @Column(name = "event_type", nullable = true)
    public Long getEventType() {
        return eventType;
    }

    public void setEventType(Long eventType) {
        this.eventType = eventType;
    }

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    public List<Alarm> getAlarms() {
        return alarms;
    }

    public void setAlarms(List<Alarm> alarms) {
        this.alarms = alarms;
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
    @JoinColumn(name = "smart_home_id", referencedColumnName = "smart_home_id", nullable = false)
    public SmartHome getSmartHome() {
        return smartHome;
    }

    public void setSmartHome(SmartHome smartHome) {
        this.smartHome = smartHome;
    }

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    public List<EventHistory> getEventHistory() {
        return eventHistory;
    }

    public void setEventHistory(List<EventHistory> eventHistory) {
        this.eventHistory = eventHistory;
    }

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Event)) return false;

        Event event = (Event) o;

        return new EqualsBuilder()
                .append(getEventId(), event.getEventId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getEventId())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("eventId", getEventId())
                .append("object", getObject())
                .append("eventType", getEventType())
                .append("subobject", getSubobject())
                .append("smartHome", getSmartHome())
                .toString();
    }
}
