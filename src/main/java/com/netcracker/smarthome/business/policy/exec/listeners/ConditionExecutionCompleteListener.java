package com.netcracker.smarthome.business.policy.exec.listeners;

import com.netcracker.smarthome.business.policy.events.Event;

public interface ConditionExecutionCompleteListener {
    void onConditionExecComplete(long conditionId, boolean result, Event event);
}
