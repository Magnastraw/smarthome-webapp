package com.netcracker.smarthome.business.policy.actions;

import com.netcracker.smarthome.ApplicationContextHolder;
import com.netcracker.smarthome.business.policy.core.PolicyEngine;
import com.netcracker.smarthome.business.policy.events.AlarmEvent;
import com.netcracker.smarthome.business.policy.events.EventType;
import com.netcracker.smarthome.business.policy.events.PolicyEvent;
import com.netcracker.smarthome.business.services.AlarmService;
import com.netcracker.smarthome.business.services.EventService;
import com.netcracker.smarthome.model.entities.Alarm;
import com.netcracker.smarthome.model.entities.AlarmSpec;
import com.netcracker.smarthome.model.entities.Event;
import com.netcracker.smarthome.model.enums.AlarmSeverity;
import org.springframework.context.ApplicationContext;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;

public class RaiseAlarmAction implements PolicyAction {
    private AlarmSpec spec;
    private AlarmSeverity severity;

    private AlarmService alarmService;
    private EventService eventService;
    private PolicyEngine policyEngine;

    public RaiseAlarmAction(Map<String, String> params) {
        initBeans();
        this.spec = alarmService.getSpecById(Long.parseLong(params.get("spec")));
        this.severity = AlarmSeverity.valueOf(params.get("severity"));
    }

    private void initBeans() {
        ApplicationContext context = ApplicationContextHolder.getApplicationContext();
        alarmService = context.getBean(AlarmService.class);
        eventService = context.getBean(EventService.class);
        policyEngine = context.getBean(PolicyEngine.class);
    }

    public void execute(PolicyEvent causalEvent) {
        Timestamp regDate = Timestamp.valueOf(LocalDateTime.now());
        if (severity.equals(AlarmSeverity.CLEAR))
            clearAlarm(causalEvent, regDate);
        else
            raiseAlarm(causalEvent, regDate);
    }

    private void clearAlarm(PolicyEvent causalEvent, Timestamp clearTime) {
        Alarm alarmProps = new Alarm();
        alarmProps.setObject(causalEvent.getObject());
        alarmProps.setSubobject(causalEvent.getSubobject());
        alarmProps.setAlarmSpec(spec);
        alarmProps.setEndTime(clearTime);
        if (alarmService.clearAlarm(alarmProps, false, true))
            sendToEngine(causalEvent, causalEvent.getDbEvent(), clearTime);
    }

    private void raiseAlarm(PolicyEvent causalEvent, Timestamp raiseTime) {
        Event dbEvent = causalEvent.getType().equals(EventType.METRIC) ? saveMetricEvent(causalEvent) : causalEvent.getDbEvent();
        Alarm alarm = new Alarm(-1L, "", "", raiseTime, null, severity, raiseTime, dbEvent, causalEvent.getObject(), causalEvent.getSubobject(), spec, null);
        if (alarmService.saveRaisedAlarm(alarm))
            sendToEngine(causalEvent, dbEvent, raiseTime);
    }

    private Event saveMetricEvent(PolicyEvent event) {
        Event metricEvent = eventService.getEvent(event.getObject().getSmartHome(), event.getObject(), event.getSubobject(), null);
        if (metricEvent == null)
            metricEvent = eventService.updateEvent(new Event(null, event.getObject(), event.getSubobject(), event.getObject().getSmartHome()));
        return metricEvent;
    }

    private void sendToEngine(PolicyEvent causalEvent, Event dbEvent, Timestamp changeTime) {
        AlarmEvent alarmEvent = new AlarmEvent(causalEvent, spec, dbEvent, severity, changeTime, -1);
        policyEngine.handleEvent(alarmEvent);
    }
}