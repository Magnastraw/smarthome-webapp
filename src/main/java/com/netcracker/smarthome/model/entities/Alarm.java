package com.netcracker.smarthome.model.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;

@Entity
@Table(name = "alarms", schema = "public", catalog = "smarthome_db")
public class Alarm implements Serializable {
    private long alarmId;
    private long clearedUserId;
    private String alarmName;
    private String alarmDescription;
    private Timestamp startTime;
    private Timestamp endTime;
    private int severity;
    private Timestamp severityChangeTime;
    private Event event;
    private AlarmSpec alarmSpec;
    private Alarm parentAlarm;
    private Collection<Alarm> subAlarms;
    private Collection<Notification> notificationssByAlarmId;

    public Alarm() {
    }

    public Alarm(long clearedUserId, String alarmName, String alarmDescription, Timestamp startTime, Timestamp endTime, int severity, Timestamp severityChangeTime, Event event, AlarmSpec alarmSpec, Alarm parentAlarm) {
        this.clearedUserId = clearedUserId;
        this.alarmName = alarmName;
        this.alarmDescription = alarmDescription;
        this.startTime = startTime;
        this.endTime = endTime;
        this.severity = severity;
        this.severityChangeTime = severityChangeTime;
        this.event = event;
        this.alarmSpec = alarmSpec;
        this.parentAlarm = parentAlarm;
    }

    @Id
    @Column(name = "alarm_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "alarm_seq")
    @SequenceGenerator(name = "alarm_seq", sequenceName = "alarms_alarm_id_seq", allocationSize = 1)
    public long getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(long alarmId) {
        this.alarmId = alarmId;
    }

    @Basic
    @Column(name = "cleared_user_id", nullable = false)
    public long getClearedUserId() {
        return clearedUserId;
    }

    public void setClearedUserId(long clearedUserId) {
        this.clearedUserId = clearedUserId;
    }

    @Basic
    @Column(name = "alarm_name", nullable = false, length = -1)
    public String getAlarmName() {
        return alarmName;
    }

    public void setAlarmName(String alarmName) {
        this.alarmName = alarmName;
    }

    @Basic
    @Column(name = "alarm_description", nullable = false, length = -1)
    public String getAlarmDescription() {
        return alarmDescription;
    }

    public void setAlarmDescription(String alarmDescription) {
        this.alarmDescription = alarmDescription;
    }

    @Basic
    @Column(name = "start_time", nullable = true)
    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "end_time", nullable = true)
    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
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
    @Column(name = "severity_change_time", nullable = false)
    public Timestamp getSeverityChangeTime() {
        return severityChangeTime;
    }

    public void setSeverityChangeTime(Timestamp severityChangeTime) {
        this.severityChangeTime = severityChangeTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Alarm alarm = (Alarm) o;

        if (alarmId != alarm.alarmId) return false;
        if (clearedUserId != alarm.clearedUserId) return false;
        if (severity != alarm.severity) return false;
        if (alarmName != null ? !alarmName.equals(alarm.alarmName) : alarm.alarmName != null) return false;
        if (alarmDescription != null ? !alarmDescription.equals(alarm.alarmDescription) : alarm.alarmDescription != null)
            return false;
        if (startTime != null ? !startTime.equals(alarm.startTime) : alarm.startTime != null) return false;
        if (endTime != null ? !endTime.equals(alarm.endTime) : alarm.endTime != null) return false;
        if (severityChangeTime != null ? !severityChangeTime.equals(alarm.severityChangeTime) : alarm.severityChangeTime != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (alarmId ^ (alarmId >>> 32));
        result = 31 * result + (int) (clearedUserId ^ (clearedUserId >>> 32));
        result = 31 * result + (alarmName != null ? alarmName.hashCode() : 0);
        result = 31 * result + (alarmDescription != null ? alarmDescription.hashCode() : 0);
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        result = 31 * result + severity;
        result = 31 * result + (severityChangeTime != null ? severityChangeTime.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumns({@JoinColumn(name = "event_id", referencedColumnName = "event_id", nullable = false), @JoinColumn(name = "object_id", referencedColumnName = "object_id", nullable = false), @JoinColumn(name = "event_type", referencedColumnName = "event_type", nullable = false)})
    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @ManyToOne
    @JoinColumn(name = "spec_id", referencedColumnName = "spec_id", nullable = false)
    public AlarmSpec getAlarmSpec() {
        return alarmSpec;
    }

    public void setAlarmSpec(AlarmSpec alarmSpec) {
        this.alarmSpec = alarmSpec;
    }

    @ManyToOne
    @JoinColumn(name = "parent_alarm_id", referencedColumnName = "alarm_id")
    public Alarm getParentAlarm() {
        return parentAlarm;
    }

    public void setParentAlarm(Alarm parentAlarm) {
        this.parentAlarm = parentAlarm;
    }

    @OneToMany(mappedBy = "parentAlarm")
    public Collection<Alarm> getSubAlarms() {
        return subAlarms;
    }

    public void setSubAlarms(Collection<Alarm> subAlarms) {
        this.subAlarms = subAlarms;
    }

    @OneToMany(mappedBy = "alarm")
    public Collection<Notification> getNotificationssByAlarmId() {
        return notificationssByAlarmId;
    }

    public void setNotificationssByAlarmId(Collection<Notification> notificationssByAlarmId) {
        this.notificationssByAlarmId = notificationssByAlarmId;
    }
}
