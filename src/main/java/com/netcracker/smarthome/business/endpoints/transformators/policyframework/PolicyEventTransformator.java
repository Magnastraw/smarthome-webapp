package com.netcracker.smarthome.business.endpoints.transformators.policyframework;

import com.netcracker.smarthome.business.endpoints.jsonentities.JsonEvent;
import com.netcracker.smarthome.business.services.AlarmService;
import com.netcracker.smarthome.business.services.AlarmSpecService;
import com.netcracker.smarthome.business.services.SmartObjectService;
import com.netcracker.smarthome.business.endpoints.transformators.ITransformator;
import com.netcracker.smarthome.business.policy.events.EventEvent;
import com.netcracker.smarthome.business.policy.events.EventType;
import com.netcracker.smarthome.model.enums.AlarmSeverity;
import org.springframework.beans.factory.annotation.Autowired;

public class PolicyEventTransformator implements ITransformator<EventEvent,JsonEvent> {
    private final SmartObjectService smartObjectService;
    private final AlarmService alarmService;

    @Autowired
    public PolicyEventTransformator(SmartObjectService smartObjectService, AlarmService alarmService) {
        this.smartObjectService = smartObjectService;
        this.alarmService = alarmService;
    }
    public EventEvent fromJsonEntity(JsonEvent jsonEntity) {
        EventEvent event = new EventEvent();
        event.setRegistryDate(jsonEntity.getRegistryDate());
        event.setType(EventType.EVENT);
        event.setObject(smartObjectService.getObjectByExternalKey(jsonEntity.getSmartHomeId(), jsonEntity.getObjectId()));
        event.setSubobject(smartObjectService.getObjectByExternalKey(jsonEntity.getSmartHomeId(), jsonEntity.getSubobjectId()));
        event.setSeverity(AlarmSeverity.valueOf(jsonEntity.getSeverity().toUpperCase()));
        event.setSpec(alarmService.getSpecById(jsonEntity.getEventType()));
        return event;
    }

    public JsonEvent toJsonEntity(EventEvent Entity) {
        throw new UnsupportedOperationException("Not supported");
    }
}
