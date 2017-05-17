package com.netcracker.smarthome.dal.repositories;

import com.netcracker.smarthome.model.entities.EventHistory;
import org.springframework.stereotype.Repository;
import javax.persistence.Query;
import java.util.List;

@Repository
public class EventHistoryRepository extends EntityRepository<EventHistory> {
    public EventHistoryRepository() {
        super(EventHistory.class);
    }

    public List<EventHistory> getEventHistory(long eventId) {
        Query query = getManager().createQuery("select eh from EventHistory eh where eh.event.eventId = :eventId");
        query.setParameter("eventId", eventId);
        return query.getResultList();
    }
}
