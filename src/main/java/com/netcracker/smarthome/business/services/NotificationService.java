package com.netcracker.smarthome.business.services;

import com.netcracker.smarthome.business.endpoints.TaskManager;
import com.netcracker.smarthome.business.notification.Email;
import com.netcracker.smarthome.dal.repositories.NotificationRepository;
import com.netcracker.smarthome.model.interfaces.NotificationObject;
import com.netcracker.smarthome.model.entities.*;
import com.netcracker.smarthome.model.enums.Channel;
import com.netcracker.smarthome.model.enums.NotificationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Map;
import org.springframework.context.ApplicationContext;
import com.netcracker.smarthome.business.notification.INotificationSender;
import com.netcracker.smarthome.dal.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class NotificationService {

    private INotificationSender notificationSender;
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private TaskManager taskManager;
    @Autowired
    private ApplicationContext appContext;

    private Notification createNotification(String notificationText, SmartHome home, Channel preferenceChannel,
            NotificationObject notificationObject) {
        Notification notification;
        if (notificationObject != null) {
            String typeNotificationObject = notificationObject.getClass().getSimpleName();
            if (typeNotificationObject.equals("Alarm")) {
                notification = new Notification(notificationText, NotificationStatus.CREATE,
                        Timestamp.from(ZonedDateTime.now().toInstant()), false, preferenceChannel, home,
                        (Alarm) notificationObject, null, null);
            } else if (typeNotificationObject.equals("Event")) {
                notification = new Notification(notificationText, NotificationStatus.CREATE,
                        Timestamp.from(ZonedDateTime.now().toInstant()), false, preferenceChannel, home, null,
                        (Event) notificationObject, null);
            } else {
                notification = new Notification(notificationText, NotificationStatus.CREATE,
                        Timestamp.from(ZonedDateTime.now().toInstant()), false, preferenceChannel, home,
                        null, null, (Metric) notificationObject);
            }
        } else {
            notification = new Notification(notificationText, NotificationStatus.CREATE,
                    Timestamp.from(ZonedDateTime.now().toInstant()), false, preferenceChannel, home,
                    null, null, null);
        }
        notificationRepository.save(notification);
        return notification;
    }

    public void sendNotification(String notificationText, SmartHome smartHome, NotificationObject notificationObject) {
        Notification notification = createNotification(notificationText, smartHome, Channel.Email, notificationObject);
        User user = smartHome.getUser();
        Map<String, INotificationSender> senders = appContext.getBeansOfType(INotificationSender.class);
        for (String channel : senders.keySet()) {
            if (channel.equals(user.getPreferChannel().toString().toLowerCase())) {
                notificationSender = senders.get(channel);
            }
        }
        notificationSender.sendNotification(notification);
    }

    public void sendNotification(String notificationText, SmartHome smartHome, Channel preferenceChannel,
            NotificationObject notificationObject) {
        Notification notification = createNotification(notificationText, smartHome, preferenceChannel, notificationObject);
        Map<String, INotificationSender> senders = appContext.getBeansOfType(INotificationSender.class);
        for (String channel : senders.keySet()) {
            if (channel.equals(preferenceChannel.toString().toLowerCase())) {
                notificationSender = senders.get(channel);
            }
        }
        if (notificationSender != null) {
            notificationSender.sendNotification(notification);
            taskManager.addUpdateEvent(smartHome.getSmartHomeId(), "updateEvent");
        } else {
            logger.error("Error in channel");
        }
    }
}
