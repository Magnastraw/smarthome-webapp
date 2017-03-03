package com.netcracker.smarthome.business.notification;

import com.netcracker.smarthome.model.entities.Notification;

public interface NotificationType {
    void sendNotification(Notification notification);
}
