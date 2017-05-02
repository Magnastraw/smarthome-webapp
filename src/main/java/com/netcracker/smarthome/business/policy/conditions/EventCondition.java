package com.netcracker.smarthome.business.policy.conditions;

import com.netcracker.smarthome.business.policy.events.EventType;
import com.netcracker.smarthome.business.policy.events.PolicyEvent;

import java.util.Map;

public class EventCondition implements PolicyCondition {
    private long spec;
    private long object;

    public EventCondition() {
    }

    public EventCondition(Map<String, String> params) {
        this.object = Long.parseLong(params.get("object"));
        this.spec = Long.parseLong(params.get("spec"));
    }

    public boolean evaluate(PolicyEvent event) {
        long eventObject = event.getSubobject() == null ? event.getObject().getSmartObjectId() : event.getSubobject().getSmartObjectId();
        return checkMatching(event) && (!isInline() || object == eventObject);
    }

    private boolean checkMatching(PolicyEvent event) {
        return (event.getType().equals(EventType.EVENT) || event.getType().equals(EventType.ALARM)) && spec == event.getSpec().getSpecId();
    }

    private boolean isInline() {
        return object > 0;
    }
}
