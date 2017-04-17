package com.netcracker.smarthome.business.policy.exec.listeners;

import com.netcracker.smarthome.business.policy.events.PolicyEvent;

public interface ConditionExecutionCompleteListener {
    void onConditionExecComplete(long conditionId, boolean result, PolicyEvent event);
}
