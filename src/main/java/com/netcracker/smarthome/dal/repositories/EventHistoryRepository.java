package com.netcracker.smarthome.dal.repositories;

import com.netcracker.smarthome.model.entities.EventHistory;
import com.netcracker.smarthome.model.entities.SmartHome;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class EventHistoryRepository extends EntityRepository<EventHistory> {
    public EventHistoryRepository() {
        super(EventHistory.class);
    }

    public List<EventHistory> getEventHistory(long eventId) {
        Query query = getManager().createNativeQuery("select * from events_history where event_id = :eventId limit 1000", EventHistory.class);
        query.setParameter("eventId", eventId);
        return query.getResultList();
    }

    public List<EventHistory> getHistoryByHome(SmartHome smartHome) {
        Query query = getManager().createQuery("select eh from EventHistory eh where eh.event.smartHome=:home");
        query.setParameter("home", smartHome);
        return query.getResultList();
    }
}
