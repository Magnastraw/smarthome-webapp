package com.netcracker.smarthome.business.policy.services;

import com.netcracker.smarthome.business.policy.events.AlarmEvent;
import com.netcracker.smarthome.business.policy.events.EventType;
import com.netcracker.smarthome.dal.repositories.AlarmRepository;
import com.netcracker.smarthome.model.entities.Alarm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class AlarmService {
    private final AlarmRepository alarmRepository;

    @Autowired
    public AlarmService(AlarmRepository alarmRepository) {
        this.alarmRepository = alarmRepository;
    }

    @Transactional
    public void deleteAlarm(long alarmId) {
        alarmRepository.delete(alarmId);
    }

    @Transactional
    public void createAlarm(Alarm alarm) {
        alarmRepository.save(alarm);
    }

    @Transactional
    public Alarm updateAlarm(Alarm alarm) {
        return alarmRepository.update(alarm);
    }

    public void clearAll(List<Alarm> alarms, long clearedUserId) {
        List<Alarm> alarmsWithChildren = new LinkedList<Alarm>();
        for (Alarm alarm : alarms) {
            List<Alarm> children = alarmRepository.getChildrenAlarmsRecursively(alarm);
            alarmsWithChildren.addAll(children);
            alarmsWithChildren.add(alarm);
        }
        List<AlarmEvent> policyAlarms = convertToPolicyAlarms(alarmsWithChildren);
        for (AlarmEvent alarmEvent : policyAlarms) {
            //send to PolicyEngine
        }
        for (Alarm alarm : alarmsWithChildren) {
            alarm.setClearedUserId(clearedUserId);
            updateAlarm(alarm);
        }
    }

    private List<AlarmEvent> convertToPolicyAlarms(List<Alarm> alarms) {
        List<AlarmEvent> result = new ArrayList<AlarmEvent>();
        for (Alarm alarm : alarms) {
            AlarmEvent alarmEvent = new AlarmEvent(EventType.ALARM, alarm.getObject(), alarm.getSubobject(), alarm.getStartTime(), alarm.getAlarmSpec(), alarm.getSeverity(), alarm.getSeverityChangeTime(), alarm.getClearedUserId());
            result.add(alarmEvent);
        }
        return result;
    }
}
