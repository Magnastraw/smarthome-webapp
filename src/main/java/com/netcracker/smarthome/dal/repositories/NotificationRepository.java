package com.netcracker.smarthome.dal.repositories;

import com.netcracker.smarthome.model.entities.Notification;
import org.springframework.stereotype.Repository;

@Repository
public class NotificationRepository extends EntityRepository<Notification> {
    public NotificationRepository() {
        super(Notification.class);
    }
}
