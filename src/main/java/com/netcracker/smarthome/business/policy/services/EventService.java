package com.netcracker.smarthome.business.policy.services;

import com.netcracker.smarthome.dal.repositories.EventRepository;
import com.netcracker.smarthome.model.entities.Event;
import com.netcracker.smarthome.model.entities.EventHistory;
import com.netcracker.smarthome.model.enums.AlarmSeverity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@Service
public class EventService {
    private final EventRepository repository;

    public EventService(EventRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Event saveEvent(Event event) {
        return repository.update(event);
    }

    @Transactional
    public Event saveWithHistory(Event event, Timestamp regDate, AlarmSeverity severity) {
        EventHistory historyRecord = new EventHistory(regDate, "", severity, "", event);
        event.getEventHistory().add(historyRecord);
        return repository.update(event);
    }
}
