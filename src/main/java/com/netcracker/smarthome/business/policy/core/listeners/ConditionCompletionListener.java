package com.netcracker.smarthome.business.policy.core.listeners;

import com.netcracker.smarthome.business.policy.events.PolicyEvent;

public interface ConditionCompletionListener {
    void onConditionComplete(long conditionId, Boolean result, PolicyEvent event);
}
