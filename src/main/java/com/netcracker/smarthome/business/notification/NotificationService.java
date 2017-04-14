package com.netcracker.smarthome.business.notification;

import com.netcracker.smarthome.dal.repositories.NotificationRepository;
import com.netcracker.smarthome.model.interfaces.NotificationObject;
import com.netcracker.smarthome.model.entities.*;
import com.netcracker.smarthome.model.enums.Channel;
import com.netcracker.smarthome.model.enums.NotificationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.time.LocalTime;

@Component
public class NotificationService {

    private NotificationSender notificationSender;

    @Autowired
    private NotificationRepository notificationRepository;

    private Notification createNotification(String notificationText, SmartHome home, Channel preferenceChannel,
                                            NotificationObject notificationObject) {
        Notification notification;
        if (notificationObject != null) {
            String typeNotificationObject = notificationObject.getClass().getSimpleName();
            if (typeNotificationObject.equals("Alarm")) {
                notification = new Notification(notificationText, NotificationStatus.CREATE,
                        Time.valueOf(LocalTime.now()), false, preferenceChannel, home,
                        (Alarm) notificationObject, null, null);
            } else if (typeNotificationObject.equals("Event")) {
                notification = new Notification(notificationText, NotificationStatus.CREATE,
                        Time.valueOf(LocalTime.now()), false, preferenceChannel, home, null,
                        (Event) notificationObject, null);
            } else {
                notification = new Notification(notificationText, NotificationStatus.CREATE,
                        Time.valueOf(LocalTime.now()), false, preferenceChannel, home,
                        null, null, (Metric) notificationObject);
            }
        } else {
            notification = new Notification(notificationText, NotificationStatus.CREATE,
                    Time.valueOf(LocalTime.now()), false, preferenceChannel, home,
                    null, null, null);
        }
        notificationRepository.save(notification);
        return notification;
    }

    public void sendNotification(String notificationText, SmartHome smartHome, NotificationObject notificationObject) {
        Notification notification = createNotification(notificationText, smartHome, Channel.Email, notificationObject);
        try {
            notificationSender = new Email();
        } catch (Exception e) {
            e.printStackTrace();
        }
        notificationSender.sendNotification(notification);
    }

    public void sendNotification(String notificationText, SmartHome smartHome, Channel preferenceChannel,
                                 NotificationObject notificationObject) {
        Notification notification = createNotification(notificationText, smartHome, preferenceChannel, notificationObject);
        try {
            notificationSender = (NotificationSender) Class.forName("com.netcracker.smarthome.business.notification."
                    + preferenceChannel.toString()).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        notificationSender.sendNotification(notification);
    }
}
