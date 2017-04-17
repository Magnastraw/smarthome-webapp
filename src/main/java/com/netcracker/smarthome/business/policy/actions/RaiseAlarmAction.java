package com.netcracker.smarthome.business.policy.actions;

import com.netcracker.smarthome.business.policy.events.AlarmEvent;
import com.netcracker.smarthome.business.policy.events.Event;
import com.netcracker.smarthome.business.policy.exec.PolicyEngine;
import com.netcracker.smarthome.dal.repositories.AlarmRepository;
import com.netcracker.smarthome.dal.repositories.AlarmSpecRepository;
import com.netcracker.smarthome.dal.repositories.EventHistoryRepository;
import com.netcracker.smarthome.model.entities.Alarm;
import com.netcracker.smarthome.model.entities.AlarmSpec;
import com.netcracker.smarthome.model.entities.EventHistory;
import com.netcracker.smarthome.model.enums.AlarmSeverity;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;

public class RaiseAlarmAction implements Action {
    //TODO: add beans variables initializing
    private AlarmSpecRepository specRepo;
    private AlarmRepository alarmRepo;
    private EventHistoryRepository eventHistoryRepo;
    private PolicyEngine policyEngine;

    private long spec;
    private AlarmSeverity severity;

    public RaiseAlarmAction() {
    }

    public RaiseAlarmAction(HashMap<String, String> params) {
        this.spec = Long.parseLong(params.get("spec"));
        this.severity = AlarmSeverity.valueOf(params.get("severity"));
    }

    public void execute(Event causalEvent) {
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        AlarmSpec alarmSpec = specRepo.get(spec);
        com.netcracker.smarthome.model.entities.Event dbEvent = causalEvent.getDbEvent();
        Alarm alarm = new Alarm(-1, "", "", now, null, severity, now, dbEvent, causalEvent.getObject(), causalEvent.getSubobject(), alarmSpec, null);
        alarmRepo.save(alarm);
        EventHistory eventHistory = new EventHistory(now, "", severity, "", dbEvent);
        eventHistoryRepo.save(eventHistory);
        AlarmEvent alarmEvent = new AlarmEvent(causalEvent, alarmSpec, severity, Timestamp.valueOf(LocalDateTime.now()), -1);
        policyEngine.handleEvent(alarmEvent);
    }
}
