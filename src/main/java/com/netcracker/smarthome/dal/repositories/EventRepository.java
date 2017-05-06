package com.netcracker.smarthome.dal.repositories;

import com.netcracker.smarthome.model.entities.Event;
import org.springframework.stereotype.Repository;
import javax.persistence.Query;
import java.util.List;

@Repository
public class EventRepository extends EntityRepository<Event> {
    public EventRepository() {
        super(Event.class);
    }

    public Event getEvent(long smartHomeId, long objectId, Long subobjectId, Long eventType) {
        String base = "select e from Event e where (e.smartHome.smartHomeId = :smartHomeId and " +
                "e.object.smartObjectId = :objectId and ";
        String conditionSubObject;
        String conditionEventType;
        if (subobjectId != null)
            conditionSubObject = "e.subobject.smartObjectId = :subobjectId and ";
        else
            conditionSubObject = "e.subobject.smartObjectId is null and ";
        if (eventType != null)
            conditionEventType = "e.eventType = :type)";
        else
            conditionEventType = "e.eventType is null)";
        Query query = getManager().createQuery(base + conditionSubObject + conditionEventType);
        query.setParameter("smartHomeId", smartHomeId);
        query.setParameter("objectId", objectId);
        if (subobjectId != null)
            query.setParameter("subobjectId", subobjectId);
        if (eventType != null)
            query.setParameter("type", eventType);
        List<Event> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }
}
