package com.netcracker.smarthome.model.keys;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class EventPK implements Serializable {
    private long eventId;
    private long objectId;
    private String eventType;

    public EventPK() {}

    public EventPK(long eventId, long objectId, String eventType) {
        this.eventId = eventId;
        this.objectId = objectId;
        this.eventType = eventType;
    }

    @Column(name = "event_id", nullable = false)
    @Id
    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    @Column(name = "object_id", nullable = false)
    @Id
    public long getObjectId() {
        return objectId;
    }

    public void setObjectId(long objectId) {
        this.objectId = objectId;
    }

    @Column(name = "event_type", nullable = false, length = -1)
    @Id
    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof EventPK)) return false;

        EventPK eventPK = (EventPK) o;

        return new EqualsBuilder()
                .append(getEventId(), eventPK.getEventId())
                .append(getObjectId(), eventPK.getObjectId())
                .append(getEventType(), eventPK.getEventType())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getEventId())
                .append(getObjectId())
                .append(getEventType())
                .toHashCode();
    }
}
