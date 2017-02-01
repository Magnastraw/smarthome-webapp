package com.netcracker.smarthome.model.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;

@Entity
@Table(name = "notifications", schema = "public", catalog = "smarthome_db")
public class Notification implements Serializable {
    private long notificationId;
    private String notificationName;
    private int notificationStatus;
    private Time time;
    private int confirm;
    private User user;
    private Alarm alarm;
    private Event event;
    private Metric metric;

    public Notification() {
    }

    public Notification(String notificationName, int notificationStatus, Time time, int confirm, User user, Alarm alarm, Event event, Metric metric) {
        this.notificationName = notificationName;
        this.notificationStatus = notificationStatus;
        this.time = time;
        this.confirm = confirm;
        this.user = user;
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
    @Column(name = "notification_name", nullable = false, length = -1)
    public String getNotificationName() {
        return notificationName;
    }

    public void setNotificationName(String notificationName) {
        this.notificationName = notificationName;
    }

    @Basic
    @Column(name = "notification_status", nullable = false)
    public int getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(int notificationStatus) {
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
    @Column(name = "confirm", nullable = false)
    public int getConfirm() {
        return confirm;
    }

    public void setConfirm(int confirm) {
        this.confirm = confirm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Notification that = (Notification) o;

        if (notificationId != that.notificationId) return false;
        if (notificationStatus != that.notificationStatus) return false;
        if (confirm != that.confirm) return false;
        if (notificationName != null ? !notificationName.equals(that.notificationName) : that.notificationName != null)
            return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (notificationId ^ (notificationId >>> 32));
        result = 31 * result + (notificationName != null ? notificationName.hashCode() : 0);
        result = 31 * result + notificationStatus;
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + confirm;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
    @JoinColumns({@JoinColumn(name = "event_id", referencedColumnName = "event_id"), @JoinColumn(name = "object_id", referencedColumnName = "object_id"), @JoinColumn(name = "event_type", referencedColumnName = "event_type")})
    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @ManyToOne
    @JoinColumn(name = "metric_id", referencedColumnName = "metric_id", nullable = false)
    public Metric getMetric() {
        return metric;
    }

    public void setMetric(Metric metric) {
        this.metric = metric;
    }
}
