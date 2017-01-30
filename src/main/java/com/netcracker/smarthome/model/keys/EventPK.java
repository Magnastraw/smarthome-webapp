package com.netcracker.smarthome.model.keys;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class EventPK implements Serializable {
    private long eventId;
    private long objectId;
    private String eventType;

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
        if (o == null || getClass() != o.getClass()) return false;

        EventPK eventPK = (EventPK) o;

        if (eventId != eventPK.eventId) return false;
        if (objectId != eventPK.objectId) return false;
        if (eventType != null ? !eventType.equals(eventPK.eventType) : eventPK.eventType != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (eventId ^ (eventId >>> 32));
        result = 31 * result + (int) (objectId ^ (objectId >>> 32));
        result = 31 * result + (eventType != null ? eventType.hashCode() : 0);
        return result;
    }
}
