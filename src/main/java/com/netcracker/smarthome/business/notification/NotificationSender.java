package com.netcracker.smarthome.business.notification;

import com.netcracker.smarthome.model.entities.Notification;

public interface NotificationSender {
    void sendNotification(Notification notification);
    String getStatus();
}
