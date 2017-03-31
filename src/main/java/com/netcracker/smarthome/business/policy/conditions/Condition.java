package com.netcracker.smarthome.business.policy.conditions;

import com.netcracker.smarthome.business.policy.events.Event;

public interface Condition {
    boolean evaluate(Event event);
}
