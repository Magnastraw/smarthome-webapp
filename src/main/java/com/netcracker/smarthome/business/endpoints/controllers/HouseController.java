package com.netcracker.smarthome.business.endpoints.controllers;

import com.fasterxml.jackson.core.JsonFactory;
import com.netcracker.smarthome.business.HomeService;
import com.netcracker.smarthome.business.endpoints.JsonRestParser;
import com.netcracker.smarthome.business.endpoints.jsonentities.*;
import com.netcracker.smarthome.business.endpoints.services.DataTypeService;
import com.netcracker.smarthome.business.endpoints.transformators.HomeParamTransformator;
import com.netcracker.smarthome.model.entities.HomeParam;
import com.netcracker.smarthome.model.entities.SmartHome;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class HouseController {
    private static final Logger LOG = LoggerFactory.getLogger(HouseController.class);

    @Autowired
    private HomeService homeService;

    @Autowired
    private DataTypeService dataTypeService;

    @RequestMapping(value = "/house",
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity sendHomeParams(@RequestParam(value="houseId", required=true) long houseId,
                                         @RequestBody String json) {
        SmartHome home = homeService.getHomeById(houseId);
        if (home == null)
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        JsonRestParser parser = new JsonRestParser();
        try {
            List<JsonParameter> parameters = parser.parseParameters(new JsonFactory().createParser(json));
            HomeParamTransformator paramTransformator = new HomeParamTransformator(dataTypeService);
            for (JsonParameter parameter : parameters) {
                HomeParam homeParam = paramTransformator.fromJsonEntity(parameter, home);
                homeService.saveParam(homeParam);
            }
        } catch (Exception ex) {
            LOG.error("Error during saving of data", ex);
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/house",
            method = RequestMethod.GET,
            produces = "application/json")
    public ResponseEntity getCommands(@RequestParam(value="houseId", required=true) long houseId) {
        SmartHome home = homeService.getHomeById(houseId);
        if (home == null)
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        /* */
        return new ResponseEntity(HttpStatus.OK);
    }
}
