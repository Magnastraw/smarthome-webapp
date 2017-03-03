package com.netcracker.smarthome.business.notification;

import com.netcracker.smarthome.dao.repositories.NotificationRepository;
import com.netcracker.smarthome.model.entities.*;
import com.netcracker.smarthome.model.enums.Channel;
import com.netcracker.smarthome.model.enums.NotificationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Time;

@Component
public class NotificationService {

    private NotificationType notificationType;

    @Autowired
    private NotificationRepository notificationRepository;

    public Notification createNotification(String notificationName, NotificationStatus notificationStatus, Time notificationTime,
                                   boolean requireConfirm, Channel typeChannel, User user,
                                   Alarm alarm, Event event, Metric metric) {
        Notification notification = new Notification(notificationName, notificationStatus, notificationTime,
                requireConfirm, typeChannel, user.getSmartHomes().get(0), alarm, event, metric);
        notificationRepository.save(notification);
        return notification;
    }

    public void sendNotificationStatus() {

    }

    public void sendSMS(Notification notification) {
        notificationType = new SMS();
        notificationType.sendNotification(notification);
    }

    public void sendEmail(Notification notification) {

    }
}
