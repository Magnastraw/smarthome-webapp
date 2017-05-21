package com.netcracker.smarthome.dal.repositories;

import com.netcracker.smarthome.model.entities.Notification;
import com.netcracker.smarthome.model.entities.SmartHome;
import com.netcracker.smarthome.model.enums.Channel;
import com.netcracker.smarthome.model.enums.NotificationStatus;
import com.netcracker.smarthome.web.notification.NotificationForView;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
public class NotificationRepository extends EntityRepository<Notification> {
    public NotificationRepository() {
        super(Notification.class);
    }

    public List<NotificationForView> getNotifications(SmartHome smartHome) {
        List<Notification> notifications = getByHome(smartHome);
        List<NotificationForView> notificationsForView = new ArrayList<NotificationForView>();
        for (Notification notification : notifications) {
            if (notification.getAlarm() != null) {
                String notificationObject = "Alarm on object " + notification.getAlarm().getObject().getName();
                if (notification.getAlarm().getSubobject() != null)
                    notificationObject += ": " + notification.getAlarm().getSubobject().getName();
                notificationsForView.add(new NotificationForView(notification.getNotificationName(),
                        notification.getNotificationStatus(), notification.getTime(), notification.getChannel(),
                        notification.getSmartHome().getName(),
                        notificationObject));
                        //"Alarm with id " + notification.getAlarm().getAlarmId()));
            } else if (notification.getEvent() != null) {
                String notificationObject = "Event on object " + notification.getEvent().getObject().getName();
                if (notification.getEvent().getSubobject() != null)
                    notificationObject += ": " + notification.getEvent().getSubobject().getName();
                notificationsForView.add(new NotificationForView(notification.getNotificationName(),
                        notification.getNotificationStatus(), notification.getTime(), notification.getChannel(),
                        notification.getSmartHome().getName(),
                        notificationObject));
                        //"Event with id " + notification.getEvent().getEventId()));
            } else if (notification.getMetric() != null) {
                String notificationObject = "Event on object " + notification.getMetric().getObject().getName();
                if (notification.getMetric().getSubobject() != null)
                    notificationObject += ": " + notification.getMetric().getSubobject().getName();
                notificationsForView.add(new NotificationForView(notification.getNotificationName(),
                        notification.getNotificationStatus(), notification.getTime(), notification.getChannel(),
                        notification.getSmartHome().getName(),
                        notificationObject));
                        //"Metric with id " + notification.getMetric().getMetricId()));
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

    public List<Notification> getByHome(SmartHome smartHome) {
        Query query = getManager().createQuery("select n from Notification n where n.smartHome = :home");
        query.setParameter("home", smartHome);
        return query.getResultList();
    }
}
