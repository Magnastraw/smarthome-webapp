package com.netcracker.smarthome.business.alarm;

import com.netcracker.smarthome.dal.repositories.EventHistoryRepository;
import com.netcracker.smarthome.model.entities.EventHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EventHistoryService {
    private final EventHistoryRepository eventHistoryRepository;

    @Autowired
    public EventHistoryService(EventHistoryRepository eventHistoryRepository) {
        this.eventHistoryRepository = eventHistoryRepository;
    }

    @Transactional
    public List<EventHistory> getEventHistory(long eventId) {
        return eventHistoryRepository.getEventHistory(eventId);
    }
}
