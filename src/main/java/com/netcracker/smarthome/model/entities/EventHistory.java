package com.netcracker.smarthome.model.entities;

import com.netcracker.smarthome.model.enums.AlarmSeverity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "events_history", schema = "public", catalog = "smarthome_db")
public class EventHistory implements Serializable {
    private long historyId;
    private Timestamp readDate;
    private String eventDescription;
    private AlarmSeverity severity;
    private String eventParameters;
    private Event event;

    public EventHistory() {
    }

    public EventHistory(Timestamp readDate, String eventDescription, AlarmSeverity severity, String eventParameters, Event event) {
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
    @Column(name = "event_description", nullable = false)
    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    @Enumerated
    @Column(name = "severity", nullable = false)
    public AlarmSeverity getSeverity() {
        return severity;
    }

    public void setSeverity(AlarmSeverity severity) {
        this.severity = severity;
    }

    @Basic
    @Column(name = "event_parameters", nullable = true)
    public String getEventParameters() {
        return eventParameters;
    }

    public void setEventParameters(String eventParameters) {
        this.eventParameters = eventParameters;
    }

    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "event_id", nullable = false)
    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof EventHistory)) return false;

        EventHistory that = (EventHistory) o;

        return new EqualsBuilder()
                .append(getHistoryId(), that.getHistoryId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getHistoryId())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("historyId", getHistoryId())
                .append("readDate", getReadDate())
                .append("eventDescription", getEventDescription())
                .append("severity", getSeverity())
                .append("eventParameters", getEventParameters())
                .append("event", getEvent())
                .toString();
    }
}
