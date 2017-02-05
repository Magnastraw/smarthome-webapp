package com.netcracker.smarthome.model.entities;

import com.netcracker.smarthome.model.enums.Channel;
import com.netcracker.smarthome.model.enums.NotificationStatus;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.io.CharArrayReader;
import java.io.Serializable;
import java.sql.Time;

@Entity
@Table(name = "notifications", schema = "public", catalog = "smarthome_db")
public class Notification implements Serializable {
    private long notificationId;
    private String notificationName;
    private NotificationStatus notificationStatus;
    private Time time;
    private boolean requireConfirm;
    private Channel channel;
    private SmartHome smartHome;
    private Alarm alarm;
    private Event event;
    private Metric metric;

    public Notification() {
    }

    public Notification(String notificationName, NotificationStatus notificationStatus, Time time, boolean requireConfirm, Channel channel, SmartHome smartHome, Alarm alarm, Event event, Metric metric) {
        this.notificationName = notificationName;
        this.notificationStatus = notificationStatus;
        this.time = time;
        this.requireConfirm = requireConfirm;
        this.channel = channel;
        this.smartHome = smartHome;
        this.alarm = alarm;
        this.event = event;
        this.metric = metric;
    }

    @Id
    @Column(name = "notification_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notif_seq")
    @SequenceGenerator(name = "notif_seq", sequenceName = "notifications_notification_id_seq", allocationSize = 1)
    public long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(long notificationId) {
        this.notificationId = notificationId;
    }

    @Basic
    @Column(name = "notification_name", nullable = false)
    public String getNotificationName() {
        return notificationName;
    }

    public void setNotificationName(String notificationName) {
        this.notificationName = notificationName;
    }

    @Column(name = "notification_status", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    public NotificationStatus getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(NotificationStatus notificationStatus) {
        this.notificationStatus = notificationStatus;
    }

    @Basic
    @Column(name = "time", nullable = false)
    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    @Basic
    @Column(name = "require_confirm", nullable = false)
    public boolean isRequireConfirm() {
        return requireConfirm;
    }

    public void setRequireConfirm(boolean requireConfirm) {
        this.requireConfirm = requireConfirm;
    }

    @Column(name = "channel", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    @ManyToOne
    @JoinColumn(name = "smart_home_id", referencedColumnName = "smart_home_id", nullable = false)
    public SmartHome getSmartHome() {
        return smartHome;
    }

    public void setSmartHome(SmartHome smartHome) {
        this.smartHome = smartHome;
    }

    @ManyToOne
    @JoinColumn(name = "alarm_id", referencedColumnName = "alarm_id")
    public Alarm getAlarm() {
        return alarm;
    }

    public void setAlarm(Alarm alarm) {
        this.alarm = alarm;
    }

    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "event_id")
    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @ManyToOne
    @JoinColumn(name = "metric_id", referencedColumnName = "metric_id")
    public Metric getMetric() {
        return metric;
    }

    public void setMetric(Metric metric) {
        this.metric = metric;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Notification)) return false;

        Notification that = (Notification) o;

        return new EqualsBuilder()
                .append(getNotificationId(), that.getNotificationId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getNotificationId())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("notificationId", getNotificationId())
                .append("notificationName", getNotificationName())
                .append("notificationStatus", getNotificationStatus())
                .append("time", getTime())
                .append("confirm", isRequireConfirm())
                .append("user", getSmartHome())
                .append("alarm", getAlarm())
                .append("event", getEvent())
                .append("metric", getMetric())
                .append("channel", getChannel())
                .toString();
    }
}
