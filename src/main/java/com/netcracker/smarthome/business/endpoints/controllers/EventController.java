package com.netcracker.smarthome.business.endpoints.controllers;

import com.netcracker.smarthome.business.HomeService;
import com.netcracker.smarthome.business.endpoints.JsonRestParser;
import com.netcracker.smarthome.business.endpoints.jsonentities.JsonEvent;
import com.netcracker.smarthome.model.entities.SmartHome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EventController {

    @Autowired
    private HomeService homeService;

    @RequestMapping(value = "/event",
            method = RequestMethod.POST,
            consumes = "application/json")
    public ResponseEntity sendAlarms(@RequestParam(value="houseId", required=true) long houseId,
                                     @RequestBody String json) {
        SmartHome home = homeService.getHomeById(houseId);
        if (home == null)
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        JsonRestParser parser = new JsonRestParser();
        List<JsonEvent> events = parser.parseEvents(json);
        if (events == null)
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        /* */
        return new ResponseEntity(HttpStatus.OK);
    }

}
