package com.netcracker.smarthome.dal.repositories;

import com.netcracker.smarthome.model.entities.Event;
import com.netcracker.smarthome.model.entities.SmartHome;
import com.netcracker.smarthome.model.entities.SmartObject;
import org.springframework.stereotype.Repository;
import javax.persistence.Query;
import java.util.List;

@Repository
public class EventRepository extends EntityRepository<Event> {
    public EventRepository() {
        super(Event.class);
    }

    public Event getEvent(SmartHome smartHome, SmartObject object, SmartObject subobject, Long eventType) {
        String base = "select e from Event e where (e.smartHome = :smartHome and " +
                "e.object = :object and ";
        String conditionSubObject;
        String conditionEventType;
        if (subobject != null)
            conditionSubObject = "e.subobject = :subobject and ";
        else
            conditionSubObject = "e.subobject is null and ";
        if (eventType != null)
            conditionEventType = "e.eventType = :type)";
        else
            conditionEventType = "e.eventType is null)";
        Query query = getManager().createQuery(base + conditionSubObject + conditionEventType);
        query.setParameter("smartHome", smartHome);
        query.setParameter("object", object);
        if (subobject != null)
            query.setParameter("subobject", subobject);
        if (eventType != null)
            query.setParameter("type", eventType);
        List<Event> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }
}
