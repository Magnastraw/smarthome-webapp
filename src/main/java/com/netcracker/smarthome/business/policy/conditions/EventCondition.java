package com.netcracker.smarthome.business.policy.conditions;

import com.netcracker.smarthome.business.policy.events.Event;

public class EventCondition implements Condition {
    public boolean evaluate(Event event) {
        // TODO: add event type check
        return false;
    }
}
