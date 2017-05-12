package com.netcracker.smarthome.business.endpoints.controllers;

import com.netcracker.smarthome.business.endpoints.TaskManager;
import com.netcracker.smarthome.business.services.HomeService;
import com.netcracker.smarthome.business.endpoints.JsonRestParser;
import com.netcracker.smarthome.business.endpoints.jsonentities.JsonAlarm;
import com.netcracker.smarthome.business.services.EventService;
import com.netcracker.smarthome.business.services.SmartObjectService;
import com.netcracker.smarthome.business.services.AlarmSpecService;
import com.netcracker.smarthome.model.entities.SmartHome;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class AlarmController {
    private static final Logger LOG = LoggerFactory.getLogger(AlarmController.class);

    @Autowired
    private HomeService homeService;
    @Autowired
    private SmartObjectService smartObjectService;
    @Autowired
    private AlarmSpecService alarmSpecService;
    @Autowired
    private EventService eventService;
    @Autowired
    private TaskManager taskManager;

    @RequestMapping(value = "/alarm",
                    method = RequestMethod.POST,
                    consumes = "application/json")
    public ResponseEntity sendAlarms(@RequestParam(value="houseId", required=true) String houseId,
                                     @RequestBody String json) {
        SmartHome home = homeService.getHomeBySecretKey(houseId);
        if (home == null)
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        JsonRestParser parser = new JsonRestParser();
        List<JsonAlarm> alarms;
        try {
            alarms = parser.parseAlarms(json);
        } catch (IOException e) {
            LOG.error("Error during parsing", e);
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        if (alarms.size() == 0)
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        /*AlarmTransformator alarmTransformator = new AlarmTransformator(smartObjectService, alarmSpecService);
        PolicyAlarmEventTransformator policyAlarmTransformator = new PolicyAlarmEventTransformator(smartObjectService, alarmSpecService);
        for (JsonAlarm item : alarms) {
            item.setSmartHomeId(houseId);
            Alarm alarm = alarmTransformator.fromJsonEntity(item);
            alarm.setAlarmName("");
            Alarm existingAlarm = eventService.getAlarm(alarm.getObject().getSmartObjectId(), alarm.getSubobject()!=null ? alarm.getSubobject().getSmartObjectId() : null, item.getSpecId());
            AlarmEvent policyAlarmEvent = policyAlarmTransformator.fromJsonEntity(item);
            try {
                if (existingAlarm != null) {
                    alarm.setAlarmId(existingAlarm.getAlarmId());
                    if (!existingAlarm.getSeverity().equals(alarm.getSeverity())) {
                        alarm.setSeverityChangeTime(item.getRegistryDate());
                        policyAlarmEvent.setSeverityChangeTime(item.getRegistryDate());
                        eventService.updateAlarm(alarm);
                    }
                } else {
                    alarm.setStartTime(item.getRegistryDate());
                    policyAlarmEvent.setRegistryDate(item.getRegistryDate());
                    eventService.saveAlarm(alarm);
                }

                Event dbEvent = eventService.getEvent(houseId, alarm.getObject().getSmartObjectId(), alarm.getSubobject().getSmartObjectId(), EventType.ALARM);
                if (dbEvent != null) {
                    dbEvent = new Event(EventType.ALARM, alarm.getObject(), alarm.getSubobject(), homeService.getHomeById(houseId));
                    eventService.saveEvent(dbEvent);
                }
                policyAlarmEvent.setDbEvent(dbEvent);

                EventHistory eventHistory = new EventHistory(item.getRegistryDate(), dbEvent, alarm.getSeverity(), null);
                eventService.saveEventHistory(eventHistory);


            } catch (Exception ex) {
                LOG.error("Error during saving of data", ex);
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        }*/
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/updateAlarm",
            method = RequestMethod.GET)
    public Boolean updateInventory(@RequestParam(value = "houseId",required = true) long houseId){
        return taskManager.getUpdateMap().get(houseId) != null && taskManager.getUpdateMap().get(houseId).remove("updateAlarm");
    }
}
