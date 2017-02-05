package com.netcracker.smarthome.dal.repositories;

import com.netcracker.smarthome.model.entities.Event;
import org.springframework.stereotype.Repository;

@Repository
public class EventRepository extends EntityRepository<Event> {
    public EventRepository() {
        super(Event.class);
    }
}
