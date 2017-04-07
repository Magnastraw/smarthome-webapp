package com.netcracker.smarthome.business.endpoints.controllers;

import com.netcracker.smarthome.business.HomeService;
import com.netcracker.smarthome.business.endpoints.JsonRestParser;
import com.netcracker.smarthome.business.endpoints.jsonentities.JsonAlarm;
import com.netcracker.smarthome.model.entities.SmartHome;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

public class AlarmController {
    private static final Logger LOG = LoggerFactory.getLogger(AlarmController.class);

    @Autowired
    private HomeService homeService;

    @RequestMapping(value = "/alarm",
                    method = RequestMethod.POST,
                    consumes = "application/json")
    public ResponseEntity sendAlarms(@RequestParam(value="houseId", required=true) long houseId,
                                     @RequestBody String json) {
        SmartHome home = homeService.getHomeById(houseId);
        if (home == null)
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        JsonRestParser parser = new JsonRestParser();
        List<JsonAlarm> alarms = parser.parseAlarms(json);
        /* */
        return new ResponseEntity(HttpStatus.OK);
    }
}
