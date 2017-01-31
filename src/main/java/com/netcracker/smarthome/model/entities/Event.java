package com.netcracker.smarthome.model.entities;

import com.netcracker.smarthome.model.keys.EventPK;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "events", schema = "public", catalog = "smarthome_db")
@IdClass(EventPK.class)
public class Event {
    private long eventId;
    private long objectId;
    private String eventType;
    private Long subobjectId;
    private Collection<Alarm> alarms;
    private User user;
    private Collection<EventHistory> eventHistories;
    private Collection<Notification> notifications;

    public Event() {
    }

    public Event(long objectId, String eventType, Long subobjectId, User user) {
        this.objectId = objectId;
        this.eventType = eventType;
        this.subobjectId = subobjectId;
        this.user = user;
    }

    @Id
    @Column(name = "event_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_seq")
    @SequenceGenerator(name = "event_seq", sequenceName = "events_event_id_seq")
    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    @Id
    @Column(name = "object_id", nullable = false)
    public long getObjectId() {
        return objectId;
    }

    public void setObjectId(long objectId) {
        this.objectId = objectId;
    }

    @Id
    @Column(name = "event_type", nullable = false, length = -1)
    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
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

        Event event = (Event) o;

        if (eventId != event.eventId) return false;
        if (objectId != event.objectId) return false;
        if (eventType != null ? !eventType.equals(event.eventType) : event.eventType != null) return false;
        if (subobjectId != null ? !subobjectId.equals(event.subobjectId) : event.subobjectId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (eventId ^ (eventId >>> 32));
        result = 31 * result + (int) (objectId ^ (objectId >>> 32));
        result = 31 * result + (eventType != null ? eventType.hashCode() : 0);
        result = 31 * result + (subobjectId != null ? subobjectId.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "event")
    public Collection<Alarm> getAlarms() {
        return alarms;
    }

    public void setAlarms(Collection<Alarm> alarms) {
        this.alarms = alarms;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @OneToMany(mappedBy = "event")
    public Collection<EventHistory> getEventHistories() {
        return eventHistories;
    }

    public void setEventHistories(Collection<EventHistory> eventHistories) {
        this.eventHistories = eventHistories;
    }

    @OneToMany(mappedBy = "event")
    public Collection<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(Collection<Notification> notifications) {
        this.notifications = notifications;
    }
}
