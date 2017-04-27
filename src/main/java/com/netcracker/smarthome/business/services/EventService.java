package com.netcracker.smarthome.business.services;

import com.netcracker.smarthome.dal.repositories.AlarmRepository;
import com.netcracker.smarthome.dal.repositories.EventHistoryRepository;
import com.netcracker.smarthome.dal.repositories.EventRepository;
import com.netcracker.smarthome.model.entities.Alarm;
import com.netcracker.smarthome.model.entities.Event;
import com.netcracker.smarthome.model.entities.EventHistory;
import com.netcracker.smarthome.model.enums.AlarmSeverity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final AlarmRepository alarmRepository;
    private final EventHistoryRepository eventHistoryRepository;

    @Autowired
    public EventService(EventRepository eventRepository, AlarmRepository alarmRepository, EventHistoryRepository eventHistoryRepository) {
        this.eventRepository = eventRepository;
        this.alarmRepository = alarmRepository;
        this.eventHistoryRepository = eventHistoryRepository;
    }

    @Transactional
    public Event saveEvent(Event event) {
        return eventRepository.update(event);
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

    @Transactional
    public List<EventHistory> getEventHistory(long eventId) {
        return eventHistoryRepository.getEventHistory(eventId);
    }

    @Transactional
    public Event saveWithHistory(Event event, Timestamp regDate, AlarmSeverity severity) {
        EventHistory historyRecord = new EventHistory(regDate, event, severity, "");
        event.getEventHistory().add(historyRecord);
        return eventRepository.update(event);
    }
}
