package com.netcracker.smarthome.business.notification;

import com.netcracker.smarthome.model.entities.Notification;

public interface INotificationSender {
    void sendNotification(Notification notification);
}
