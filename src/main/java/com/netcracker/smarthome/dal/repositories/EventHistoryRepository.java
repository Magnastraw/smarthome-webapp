package com.netcracker.smarthome.dal.repositories;

import com.netcracker.smarthome.model.entities.EventHistory;
import org.springframework.stereotype.Repository;

@Repository
public class EventHistoryRepository extends EntityRepository<EventHistory> {
    public EventHistoryRepository() {
        super(EventHistory.class);
    }
}
