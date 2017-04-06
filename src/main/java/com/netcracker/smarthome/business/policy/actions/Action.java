package com.netcracker.smarthome.business.policy.actions;

import com.netcracker.smarthome.business.policy.events.Event;

public interface Action {
    void execute(Event causalEvent);
}
