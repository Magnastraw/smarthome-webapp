package com.netcracker.smarthome.business.policy.conditions;

import com.netcracker.smarthome.business.policy.events.PolicyEvent;

public interface PolicyCondition {
    boolean evaluate(PolicyEvent event);
}
