package com.netcracker.smarthome.business.policy.services;

import com.netcracker.smarthome.business.policy.events.AlarmEvent;
import com.netcracker.smarthome.business.policy.events.Event;
import com.netcracker.smarthome.business.policy.events.EventType;
import com.netcracker.smarthome.dal.repositories.AlarmRepository;
import com.netcracker.smarthome.dal.repositories.UserRepository;
import com.netcracker.smarthome.model.entities.Alarm;
import com.netcracker.smarthome.model.enums.AlarmSeverity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class AlarmService {
    private final AlarmRepository alarmRepository;
    private final UserRepository userRepository;

    @Autowired
    public AlarmService(AlarmRepository alarmRepository, UserRepository userRepository) {
        this.alarmRepository = alarmRepository;
        this.userRepository = userRepository;
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

    @Transactional
    public void clearAll(List<Alarm> alarms, long clearedUserId) {
        List<Alarm> alarmsWithChildren = new LinkedList<Alarm>();
        for (Alarm alarm : alarms) {
            List<Alarm> children = alarmRepository.getChildrenAlarmsRecursively(alarm);
            alarmsWithChildren.addAll(children);
            alarmsWithChildren.add(alarm);
        }
        for (Alarm alarm : alarmsWithChildren) {
            alarm.setClearedUserId(clearedUserId);
            alarm.setSeverity(AlarmSeverity.CLEARED);
            updateAlarm(alarm);
        }
//        List<AlarmEvent> policyAlarms = convertToPolicyAlarms(alarmsWithChildren);
//        for (AlarmEvent alarmEvent : policyAlarms) {
//            //send to PolicyEngine to check
//        }
    }

    @Transactional
    public void clear(Alarm alarmToClear, long clearedUserId) {
        List<Alarm> alarmWithChildren = new LinkedList<Alarm>();
        alarmWithChildren.add(alarmToClear);
        alarmWithChildren.addAll(alarmRepository.getChildrenAlarmsRecursively(alarmToClear));
        for (Alarm alarm : alarmWithChildren) {
            alarm.setClearedUserId(clearedUserId);
            alarm.setSeverity(AlarmSeverity.CLEARED);
            updateAlarm(alarm);
        }
//      List<AlarmEvent> policyAlarms = convertToPolicyAlarms(alarmsWithChildren);
//        for (AlarmEvent alarmEvent : policyAlarms) {
//            //send to PolicyEngine to check
//        }
    }

    @Transactional
    public void clearBySystem(Alarm alarmToClear) {
        long clearedUserId = userRepository.getByEmail("system@system.com").getUserId();
        clear(alarmToClear, clearedUserId);
    }

    private List<AlarmEvent> convertToPolicyAlarms(List<Alarm> alarms) {
        List<AlarmEvent> result = new ArrayList<AlarmEvent>();
        for (Alarm alarm : alarms) {
            AlarmEvent alarmEvent = new AlarmEvent(EventType.ALARM, alarm.getObject(), alarm.getSubobject(), alarm.getStartTime(), alarm.getAlarmSpec(), alarm.getEvent(), alarm.getSeverity(), alarm.getSeverityChangeTime(), alarm.getClearedUserId());
            result.add(alarmEvent);
        }
        return result;
    }
}
