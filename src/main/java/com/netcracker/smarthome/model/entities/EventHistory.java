package com.netcracker.smarthome.model.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "events_history", schema = "public", catalog = "smarthome_db")
public class EventHistory implements Serializable {
    private long historyId;
    private Timestamp readDate;
    private String eventDescription;
    private int severity;
    private String eventParameters;
    private Event event;

    public EventHistory() {
    }

    public EventHistory(Timestamp readDate, String eventDescription, int severity, String eventParameters, Event event) {
        this.readDate = readDate;
        this.eventDescription = eventDescription;
        this.severity = severity;
        this.eventParameters = eventParameters;
        this.event = event;
    }

    @Id
    @Column(name = "history_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ev_histiory_seq")
    @SequenceGenerator(name = "ev_histiory_seq", sequenceName = "events_history_id_seq", allocationSize = 1)
    public long getHistoryId() {
        return historyId;
    }

    public void setHistoryId(long historyId) {
        this.historyId = historyId;
    }

    @Basic
    @Column(name = "read_date", nullable = false)
    public Timestamp getReadDate() {
        return readDate;
    }

    public void setReadDate(Timestamp readDate) {
        this.readDate = readDate;
    }

    @Basic
    @Column(name = "event_description", nullable = false, length = -1)
    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    @Basic
    @Column(name = "severity", nullable = false)
    public int getSeverity() {
        return severity;
    }

    public void setSeverity(int severity) {
        this.severity = severity;
    }

    @Basic
    @Column(name = "event_parameters", nullable = true, length = -1)
    public String getEventParameters() {
        return eventParameters;
    }

    public void setEventParameters(String eventParameters) {
        this.eventParameters = eventParameters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventHistory that = (EventHistory) o;

        if (historyId != that.historyId) return false;
        if (severity != that.severity) return false;
        if (readDate != null ? !readDate.equals(that.readDate) : that.readDate != null) return false;
        if (eventDescription != null ? !eventDescription.equals(that.eventDescription) : that.eventDescription != null)
            return false;
        if (eventParameters != null ? !eventParameters.equals(that.eventParameters) : that.eventParameters != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (historyId ^ (historyId >>> 32));
        result = 31 * result + (readDate != null ? readDate.hashCode() : 0);
        result = 31 * result + (eventDescription != null ? eventDescription.hashCode() : 0);
        result = 31 * result + severity;
        result = 31 * result + (eventParameters != null ? eventParameters.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumns({@JoinColumn(name = "object_id", referencedColumnName = "event_id", nullable = false), @JoinColumn(name = "event_id", referencedColumnName = "object_id", nullable = false), @JoinColumn(name = "event_type", referencedColumnName = "event_type", nullable = false)})
    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
