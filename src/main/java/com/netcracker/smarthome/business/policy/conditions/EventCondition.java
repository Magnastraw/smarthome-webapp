package com.netcracker.smarthome.business.policy.conditions;

import com.netcracker.smarthome.business.policy.events.EventType;
import com.netcracker.smarthome.business.policy.events.PolicyEvent;
import com.netcracker.smarthome.model.entities.SmartObject;

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

    public boolean evaluate(PolicyEvent event) {
        return checkMatching(event) && (!isInline() || object == getEventObject(event).getSmartObjectId());
    }

    private boolean checkMatching(PolicyEvent event) {
        return (event.getType().equals(EventType.EVENT) || event.getType().equals(EventType.ALARM)) && spec == event.getSpec().getSpecId();
    }

    private SmartObject getEventObject(PolicyEvent event) {
        return event.getSubobject() == null ? event.getObject() : event.getSubobject();
    }

    private boolean isInline() {
        return object > 0;
    }
}
