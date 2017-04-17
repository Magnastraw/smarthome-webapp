package com.netcracker.smarthome.business.policy.actions;

import com.netcracker.smarthome.business.policy.events.PolicyEvent;

public interface Action {
    void execute(PolicyEvent causalEvent);
}
