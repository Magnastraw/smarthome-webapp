package com.netcracker.smarthome.dal.repositories;

import com.netcracker.smarthome.business.policy.events.EventType;
import com.netcracker.smarthome.model.entities.Event;
import org.springframework.stereotype.Repository;
import javax.persistence.Query;
import java.util.List;

@Repository
public class EventRepository extends EntityRepository<Event> {
    public EventRepository() {
        super(Event.class);
    }

    public Event getEvent(long smartHomeId, long objectId, Long subobjectId, EventType eventType) {
        Query query;
        if (subobjectId != null) {
            query = getManager().createQuery("select e from Event e where (e.smartHome.smartHomeId = :smartHomeId and " +
                    "e.object.smartObjectId = :objectId and " +
                    "e.subobject.smartObjectId = :subobjectId and " +
                    "e.eventType = :type)");
            query.setParameter("smartHomeId", smartHomeId);
            query.setParameter("objectId", objectId);
            query.setParameter("subobjectId", subobjectId);
            query.setParameter("type", eventType);
        }
        else {
            query = getManager().createQuery("select e from Event e where (e.smartHome.smartHomeId = :smartHomeId and " +
                    "e.object.smartObjectId = :objectId and " +
                    "e.subobject.smartObjectId is null and " +
                    "e.eventType = :type)");
            query.setParameter("smartHomeId", smartHomeId);
            query.setParameter("objectId", objectId);
            query.setParameter("type", eventType);
        }
        List<Event> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }
}
