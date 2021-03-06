package com.netcracker.smarthome.business.endpoints.controllers;

import com.netcracker.smarthome.business.endpoints.TaskManager;
import com.netcracker.smarthome.business.policy.core.PolicyEngine;
import com.netcracker.smarthome.business.services.*;
import com.netcracker.smarthome.business.endpoints.JsonRestParser;
import com.netcracker.smarthome.business.endpoints.jsonentities.JsonEvent;
import com.netcracker.smarthome.business.endpoints.transformators.EventTransformator;
import com.netcracker.smarthome.business.endpoints.transformators.policyframework.PolicyEventTransformator;
import com.netcracker.smarthome.business.policy.events.EventEvent;
import com.netcracker.smarthome.model.entities.Event;
import com.netcracker.smarthome.model.entities.EventHistory;
import com.netcracker.smarthome.model.entities.SmartHome;
import com.netcracker.smarthome.model.enums.AlarmSeverity;
import io.swagger.annotations.ApiOperation;
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
    @Autowired
    PolicyEngine policyEngine;
    @Autowired
    TaskManager taskManager;


    @RequestMapping(value = "/event",
            method = RequestMethod.POST,
            consumes = "application/json")
    @ApiOperation(value = "Send event")
    public ResponseEntity sendAlarms(@RequestParam(value="houseId", required=true) String houseId,
                                     @RequestBody String json) {
        LOG.info("POST /event\nBody:\n" + json);
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
        PolicyEventTransformator policyEventTransformator = new PolicyEventTransformator(smartObjectService, alarmService);
        for (JsonEvent item : events) {
            item.setSmartHomeId(home.getSmartHomeId());
            Event event = eventTransformator.fromJsonEntity(item);
            Event existingEvent = eventService.getEvent(home, event.getObject(), event.getSubobject(), event.getEventType());
            try {
                if (existingEvent != null) {
                    event.setEventId(existingEvent.getEventId());
                } else {
                    eventService.saveEvent(event);
                }

                EventHistory eventHistory = new EventHistory(item.getRegistryDate(), event, AlarmSeverity.valueOf(item.getSeverity().toUpperCase()), item.getEventParameters());
                eventService.saveEventHistory(eventHistory);

                EventEvent policyEvent = policyEventTransformator.fromJsonEntity(item);
                policyEvent.setDbEvent(eventService.getEvent(home, event.getObject(), event.getSubobject(), event.getEventType()));
                policyEngine.handleEvent(policyEvent);
            } catch (Exception ex) {
                LOG.error("Error during saving of data", ex);
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        }
        taskManager.addUpdateEvent(home.getSmartHomeId(),"updateEvent");
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/updateEvent",
            method = RequestMethod.GET)
    @ApiOperation(value = "Update event table")
    public Boolean updateEventTable(@RequestParam(value = "houseId",required = true) long houseId){
        return taskManager.getUpdateMap().get(houseId) != null && taskManager.getUpdateMap().get(houseId).remove("updateEvent");
    }

    @RequestMapping(value = "/updateAlarm",
            method = RequestMethod.GET)
    @ApiOperation(value = "Update alarm table")
    public Boolean updateAlarmTable(@RequestParam(value = "houseId",required = true) long houseId){
        return taskManager.getUpdateMap().get(houseId) != null && taskManager.getUpdateMap().get(houseId).remove("updateAlarm");
    }
}
