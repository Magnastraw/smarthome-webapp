package com.netcracker.smarthome.business.policy.conditions;

import com.netcracker.smarthome.business.policy.events.Event;
import com.netcracker.smarthome.business.policy.events.EventType;

import java.util.HashMap;

public class EventCondition implements Condition {
    private long spec;
    private long object;

    public EventCondition() {
    }

    public EventCondition(HashMap<String, String> params) {
        this.object = Long.parseLong(params.get("object"));
        this.spec = Long.parseLong(params.get("spec"));
    }

    public boolean evaluate(Event event) {
        return checkMatching(event) && (!isInline() || object == event.getSubobject().getSmartObjectId());
    }

    private boolean checkMatching(Event event) {
        return (event.getType().equals(EventType.EVENT) || event.getType().equals(EventType.ALARM)) && spec == event.getSpec().getSpecId();
    }

    private boolean isInline() {
        return object > 0;
    }
}
