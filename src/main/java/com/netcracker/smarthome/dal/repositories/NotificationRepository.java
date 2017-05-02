package com.netcracker.smarthome.dal.repositories;

import com.netcracker.smarthome.model.entities.Notification;
import com.netcracker.smarthome.model.enums.Channel;
import com.netcracker.smarthome.model.enums.NotificationStatus;
import com.netcracker.smarthome.web.notification.NotificationForView;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class NotificationRepository extends EntityRepository<Notification> {
    public NotificationRepository() {
        super(Notification.class);
    }

    public List<NotificationForView> getNotifications() {
        List<Notification> notifications = getAll();
        List<NotificationForView> notificationsForView = new ArrayList<NotificationForView>();
        for (Notification notification : notifications) {
            if (notification.getAlarm() != null) {
                notificationsForView.add(new NotificationForView(notification.getNotificationName(),
                        notification.getNotificationStatus(), notification.getTime(), notification.getChannel(),
                        notification.getSmartHome().getName(), "Alarm with id " + notification.getAlarm().getAlarmId()));
            } else if (notification.getEvent() != null) {
                notificationsForView.add(new NotificationForView(notification.getNotificationName(),
                        notification.getNotificationStatus(), notification.getTime(), notification.getChannel(),
                        notification.getSmartHome().getName(), "Event with id " + notification.getEvent().getEventId()));
            } else if (notification.getMetric() != null) {
                notificationsForView.add(new NotificationForView(notification.getNotificationName(),
                        notification.getNotificationStatus(), notification.getTime(), notification.getChannel(),
                        notification.getSmartHome().getName(), "Metric with id " + notification.getMetric().getMetricId()));
            } else {
                notificationsForView.add(new NotificationForView(notification.getNotificationName(),
                        notification.getNotificationStatus(), notification.getTime(), notification.getChannel(),
                        notification.getSmartHome().getName(), "There is no object"));
            }
        }
        return notificationsForView;
    }

    public List<String> getChannels() {
        List<String> channels = new ArrayList<String>();
        for (Channel ch : Channel.values()) {
            channels.add(ch.name());
        }
        return channels;
    }

    public List<String> getStatuses() {
        List<String> statuses = new ArrayList<String>();
        for (NotificationStatus s : NotificationStatus.values()) {
            statuses.add(s.name());
        }
        return statuses;
    }
}
