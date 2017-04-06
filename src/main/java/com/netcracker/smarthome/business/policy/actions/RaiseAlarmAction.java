package com.netcracker.smarthome.business.policy.actions;

import com.netcracker.smarthome.business.policy.events.AlarmEvent;
import com.netcracker.smarthome.business.policy.events.Event;
import com.netcracker.smarthome.model.entities.AlarmSpec;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class RaiseAlarmAction implements Action {
    private AlarmSpec spec;
    private Integer severity;

    public RaiseAlarmAction() {
    }

    public RaiseAlarmAction(AlarmSpec spec, Integer severity) {
        this.spec = spec;
        this.severity = severity;
    }

    public void execute(Event causalEvent) {
        AlarmEvent alarmEvent = new AlarmEvent(causalEvent, spec, severity, Timestamp.valueOf(LocalDateTime.now()), -1);
        //TODO : add alarm raising (push to event queue)
    }
}
