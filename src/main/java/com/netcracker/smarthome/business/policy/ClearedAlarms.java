package com.netcracker.smarthome.business.policy;

import com.netcracker.smarthome.business.policy.events.AlarmEvent;
import com.netcracker.smarthome.business.policy.events.EventType;
import com.netcracker.smarthome.business.policy.services.AlarmService;
import com.netcracker.smarthome.model.entities.Alarm;
import com.netcracker.smarthome.model.entities.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ClearedAlarms {
    @Autowired
    private AlarmService alarmService;

    public ClearedAlarms(AlarmService alarmService) {
        this.alarmService = alarmService;
    }

    public void clearAll(List<Alarm> alarms, long clearedUserId) {
        List<AlarmEvent> policyAlarms = convertToPolicyAlarms(alarms);
        for (AlarmEvent alarmEvent : policyAlarms) {
            //send to PolicyEngine
            //return executed policyAlarms + executed children
        }
        List<Alarm> updateAlarms = convertToDBAlarms(policyAlarms, clearedUserId);
        for (Alarm alarm : updateAlarms) {
            alarmService.updateAlarm(alarm);
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

    private List<Alarm> convertToDBAlarms(List<AlarmEvent> alarmEvents, long clearedUserId) {
        List<Alarm> result = new ArrayList<Alarm>();
        for (AlarmEvent alarmEvent : alarmEvents) {
            //add "name", "description", "event", "parentAlarm" attribute to AlarmEvent class
            Alarm alarm = new Alarm(clearedUserId, "alarm", "some_discription", alarmEvent.getRegistryDate(), alarmEvent.getSeverityChangeTime(), alarmEvent.getSeverity(), alarmEvent.getSeverityChangeTime(), new Event(), alarmEvent.getObject(), alarmEvent.getSubobject(), alarmEvent.getAlarmSpec(), new Alarm() );
            result.add(alarm);
        }
        return result;
    }
}













