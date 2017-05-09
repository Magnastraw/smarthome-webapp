package com.netcracker.smarthome.business.services;

import com.netcracker.smarthome.business.policy.events.EventType;
import com.netcracker.smarthome.dal.repositories.AlarmRepository;
import com.netcracker.smarthome.dal.repositories.EventHistoryRepository;
import com.netcracker.smarthome.dal.repositories.EventRepository;
import com.netcracker.smarthome.model.entities.Alarm;
import com.netcracker.smarthome.model.entities.Event;
import com.netcracker.smarthome.model.entities.EventHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private AlarmRepository alarmRepository;

    @Autowired
    private EventHistoryRepository eventHistoryRepository;

    @Transactional
    public void saveEvent(Event event) {
        eventRepository.save(event);
    }

    @Transactional
    public void saveEventHistory(EventHistory eventHistory) {
        eventHistoryRepository.save(eventHistory);
    }

    @Transactional(readOnly = true)
    public Event getEvent(long smartHomeId, long objectId,  Long subobjectId, Long eventType) {
        return eventRepository.getEvent(smartHomeId, objectId, subobjectId, eventType);
    }

    @Transactional(readOnly = true)
    public Alarm getAlarm(long objectId, Long subobjectId, long specId) {
        return alarmRepository.getAlarm(objectId, subobjectId, specId);
    }

    @Transactional
    public void saveAlarm(Alarm alarm) {
        alarmRepository.save(alarm);
    }

    @Transactional
    public void updateAlarm(Alarm alarm) {
        alarmRepository.update(alarm);
    }

    @Transactional(readOnly = true)
    public List<EventHistory> getEventHistory(long eventId) {
        return eventHistoryRepository.getEventHistory(eventId);
    }

}
