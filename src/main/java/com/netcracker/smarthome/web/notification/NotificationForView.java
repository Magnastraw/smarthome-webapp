package com.netcracker.smarthome.web.notification;

import com.netcracker.smarthome.model.enums.Channel;
import com.netcracker.smarthome.model.enums.NotificationStatus;

import java.sql.Time;

public class NotificationForView {

    private String notificationName;
    private NotificationStatus notificationStatus;
    private Time time;
    private Channel channel;
    private String smartHome;
    private String notificationObject;

    public NotificationForView(String notificationName, NotificationStatus notificationStatus, Time time,
                               Channel channel, String smartHome, String notificationObject) {
        this.notificationName = notificationName;
        this.notificationStatus = notificationStatus;
        this.time = time;
        this.channel = channel;
        this.smartHome = smartHome;
        this.notificationObject = notificationObject;
    }

    public String getNotificationName() {
        return notificationName;
    }

    public void setNotificationName(String notificationName) {
        this.notificationName = notificationName;
    }

    public NotificationStatus getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(NotificationStatus notificationStatus) {
        this.notificationStatus = notificationStatus;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public String getSmartHome() {
        return smartHome;
    }

    public void setSmartHome(String smartHome) {
        this.smartHome = smartHome;
    }

    public String getNotificationObject() {
        return notificationObject;
    }

    public void setNotificationObject(String notificationObject) {
        this.notificationObject = notificationObject;
    }
}
