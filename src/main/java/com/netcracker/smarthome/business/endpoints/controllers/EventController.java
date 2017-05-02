package com.netcracker.smarthome.business.endpoints.controllers;

import com.netcracker.smarthome.business.HomeService;
import com.netcracker.smarthome.business.alarm.AlarmService;
import com.netcracker.smarthome.business.endpoints.JsonRestParser;
import com.netcracker.smarthome.business.endpoints.jsonentities.JsonEvent;
import com.netcracker.smarthome.business.endpoints.services.EventService;
import com.netcracker.smarthome.business.endpoints.services.SmartObjectService;
import com.netcracker.smarthome.business.endpoints.transformators.EventTransformator;
import com.netcracker.smarthome.business.endpoints.transformators.policyframework.PolicyEventTransformator;
import com.netcracker.smarthome.business.policy.events.EventEvent;
import com.netcracker.smarthome.business.policy.events.EventType;
import com.netcracker.smarthome.model.entities.Event;
import com.netcracker.smarthome.model.entities.EventHistory;
import com.netcracker.smarthome.model.entities.SmartHome;
import com.netcracker.smarthome.model.enums.AlarmSeverity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class EventController {
    private static final Logger LOG = LoggerFactory.getLogger(EventController.class);

    @Autowired
    private HomeService homeService;
    @Autowired
    private SmartObjectService smartObjectService;
    @Autowired
    private EventService eventService;
    @Autowired
    AlarmService alarmService;

    @RequestMapping(value = "/event",
            method = RequestMethod.POST,
            consumes = "application/json")
    public ResponseEntity sendAlarms(@RequestParam(value="houseId", required=true) String houseId,
                                     @RequestBody String json) {
        SmartHome home = homeService.getHomeBySecretKey(houseId);
        if (home == null)
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        JsonRestParser parser = new JsonRestParser();
        List<JsonEvent> events;
        try {
            events = parser.parseEvents(json);
        } catch (IOException e) {
            LOG.error("Error during parsing", e);
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        if (events.size() == 0)
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        EventTransformator eventTransformator = new EventTransformator(smartObjectService);
        PolicyEventTransformator policyEventTransformator = new PolicyEventTransformator(smartObjectService);
        for (JsonEvent item : events) {
            /*Event event = eventService.getEvent(houseId, item.getObjectId(), item.getSubobjectId(), EventType.valueOf(item.getEventType().toUpperCase()).ordinal());
            if (event == null) {
                item.setSmartHomeId(houseId);
                event = eventTransformator.fromJsonEntity(item);
                eventService.saveEvent(event);
            }*/
            item.setSmartHomeId(home.getSmartHomeId());
            Event event = eventTransformator.fromJsonEntity(item);
            Event existingEvent = eventService.getEvent(home.getSmartHomeId(), event.getObject().getSmartObjectId(), event.getSubobject()!=null ? event.getSubobject().getSmartObjectId() : null, null);
            try {
                if (existingEvent != null) {
                    event.setEventId(existingEvent.getEventId());
                } else {
                    eventService.saveEvent(event);
                }

                EventHistory eventHistory = new EventHistory(item.getRegistryDate(), event, AlarmSeverity.valueOf(item.getSeverity().toUpperCase()), item.getEventParameters());
                eventService.saveEventHistory(eventHistory);

                EventEvent policyEvent = policyEventTransformator.fromJsonEntity(item);
                policyEvent.setDbEvent(event);
                /* */
            } catch (Exception ex) {
                LOG.error("Error during saving of data", ex);
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
