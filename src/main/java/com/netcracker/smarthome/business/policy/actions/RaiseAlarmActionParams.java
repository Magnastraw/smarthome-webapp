package com.netcracker.smarthome.business.policy.actions;

import com.netcracker.smarthome.business.policy.events.Event;

public class RaiseAlarmActionParams extends ActionParams {
    private Event causeEvent;

    public RaiseAlarmActionParams(Event causeEvent) {
        this.causeEvent = causeEvent;
    }

    public Event getCauseEvent() {
        return causeEvent;
    }

    public void setCauseEvent(Event causeEvent) {
        this.causeEvent = causeEvent;
    }
}
