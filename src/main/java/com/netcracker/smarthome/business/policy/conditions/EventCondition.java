package com.netcracker.smarthome.business.policy.conditions;

import com.netcracker.smarthome.business.policy.events.Event;
import com.netcracker.smarthome.business.policy.events.EventType;
import com.netcracker.smarthome.model.entities.AlarmSpec;
import com.netcracker.smarthome.model.entities.SmartObject;

public class EventCondition implements Condition {
    private AlarmSpec spec;
    private SmartObject object;

    public EventCondition() {
    }

    public EventCondition(AlarmSpec spec, SmartObject object) {
        this.object = object;
        this.spec = spec;
    }

    public boolean evaluate(Event event) {
        return checkMatching(event) && (!isInline() || object.equals(event.getSubobject()));
    }

    private boolean checkMatching(Event event) {
        return (event.getType().equals(EventType.EVENT) || event.getType().equals(EventType.ALARM)) && spec.equals(event.getSpec());
    }

    private boolean isInline() {
        return object != null;
    }
}
