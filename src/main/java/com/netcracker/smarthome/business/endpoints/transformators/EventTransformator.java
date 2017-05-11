package com.netcracker.smarthome.business.endpoints.transformators;

import com.netcracker.smarthome.business.endpoints.jsonentities.JsonEvent;
import com.netcracker.smarthome.business.services.SmartObjectService;
import com.netcracker.smarthome.model.entities.Event;
import org.springframework.beans.factory.annotation.Autowired;

public class EventTransformator implements ITransformator<Event,JsonEvent> {
    private final SmartObjectService smartObjectService;

    @Autowired
    public EventTransformator(SmartObjectService smartObjectService) {
        this.smartObjectService = smartObjectService;
    }

    public Event fromJsonEntity(JsonEvent jsonEntity) {
        Event event = new Event();
        event.setObject(smartObjectService.getObjectByExternalKey(jsonEntity.getSmartHomeId(), jsonEntity.getObjectId()));
        event.setSubobject(smartObjectService.getObjectByExternalKey(jsonEntity.getSmartHomeId(), jsonEntity.getSubobjectId()));
        event.setSmartHome(event.getObject().getSmartHome());
        event.setEventType(jsonEntity.getEventType());
        return event;
    }

    public JsonEvent toJsonEntity(Event Entity) {
        throw new UnsupportedOperationException("Not supported");
    }
}
