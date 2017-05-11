package com.netcracker.smarthome.business.policy.conditions;

import com.netcracker.smarthome.business.policy.events.EventEvent;
import com.netcracker.smarthome.business.policy.events.EventType;
import com.netcracker.smarthome.business.policy.events.PolicyEvent;
import com.netcracker.smarthome.model.enums.AlarmSeverity;

import java.util.Map;

public class EventCondition implements PolicyCondition {
    private EventType type;
    private long spec;
    private long object;
    private AlarmSeverity severity;

    public EventCondition(Map<String, String> params) {
        this.type = EventType.valueOf(params.get("type").toUpperCase());
        this.spec = Long.parseLong(params.get("spec"));
        this.object = params.containsKey("object") ? Long.parseLong(params.get("object")) : -1;
        this.severity = params.containsKey("severity") ? AlarmSeverity.valueOf(params.get("severity").toUpperCase()) : null;
    }

    public Boolean evaluate(PolicyEvent event) {
        if (!event.getType().equals(type))
            return null;
        long eventObject = event.getSubobject() == null ? event.getObject().getSmartObjectId() : event.getSubobject().getSmartObjectId();
        return spec == event.getSpec().getSpecId() && (!isInline() || object == eventObject) && (severity == null || severity.equals(((EventEvent) event).getSeverity()));
    }

    private boolean isInline() {
        return object > 0;
    }
}
