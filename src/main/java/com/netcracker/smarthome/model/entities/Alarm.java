package com.netcracker.smarthome.model.entities;

import com.netcracker.smarthome.model.enums.AlarmSeverity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "alarms", schema = "public", catalog = "smarthome_db")
public class Alarm implements Serializable {
    private long alarmId;
    private long clearedUserId;
    private String alarmName;
    private String alarmDescription;
    private Timestamp startTime;
    private Timestamp endTime;
    private AlarmSeverity severity;
    private Timestamp severityChangeTime;
    private Event event;
    private SmartObject object;
    private SmartObject subobject;
    private AlarmSpec alarmSpec;
    private Alarm parentAlarm;
    private List<Alarm> subAlarms;
    private List<Notification> notifications;

    public Alarm() {
    }

    public Alarm(long clearedUserId, String alarmName, String alarmDescription, Timestamp startTime, Timestamp endTime, AlarmSeverity severity, Timestamp severityChangeTime, Event event, SmartObject object, SmartObject subobject, AlarmSpec alarmSpec, Alarm parentAlarm) {
        this.clearedUserId = clearedUserId;
        this.alarmName = alarmName;
        this.alarmDescription = alarmDescription;
        this.startTime = startTime;
        this.endTime = endTime;
        this.severity = severity;
        this.severityChangeTime = severityChangeTime;
        this.event = event;
        this.object = object;
        this.subobject = subobject;
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
    @Column(name = "alarm_name", nullable = false)
    public String getAlarmName() {
        return alarmName;
    }

    public void setAlarmName(String alarmName) {
        this.alarmName = alarmName;
    }

    @Basic
    @Column(name = "alarm_description", nullable = false)
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
    @Enumerated(EnumType.ORDINAL)
    public AlarmSeverity getSeverity() {
        return severity;
    }

    public void setSeverity(AlarmSeverity severity) {
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

    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "event_id", nullable = false)
    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
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

    @OneToMany(mappedBy = "parentAlarm", cascade = CascadeType.ALL)
    public List<Alarm> getSubAlarms() {
        return subAlarms;
    }

    public void setSubAlarms(List<Alarm> subAlarms) {
        this.subAlarms = subAlarms;
    }

    @OneToMany(mappedBy = "alarm", cascade = CascadeType.ALL)
    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Alarm)) return false;

        Alarm alarm = (Alarm) o;

        return new EqualsBuilder()
                .append(getAlarmId(), alarm.getAlarmId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getAlarmId())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("alarmId", getAlarmId())
                .append("clearedUserId", getClearedUserId())
                .append("alarmName", getAlarmName())
                .append("alarmDescription", getAlarmDescription())
                .append("startTime", getStartTime())
                .append("endTime", getEndTime())
                .append("severity", getSeverity())
                .append("severityChangeTime", getSeverityChangeTime())
                .append("event", getEvent())
                .append("object", getObject())
                .append("subobject", getSubobject())
                .append("alarmSpec", getAlarmSpec())
                .append("parentAlarm", getParentAlarm())
                .toString();
    }
}
