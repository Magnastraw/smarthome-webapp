package com.netcracker.smarthome.business.endpoints.controllers;

import com.netcracker.smarthome.business.services.HomeService;
import com.netcracker.smarthome.business.endpoints.JsonRestParser;
import com.netcracker.smarthome.business.endpoints.jsonentities.*;
import com.netcracker.smarthome.business.services.DataTypeService;
import com.netcracker.smarthome.business.endpoints.transformators.HomeParamTransformator;
import com.netcracker.smarthome.model.entities.HomeParam;
import com.netcracker.smarthome.model.entities.SmartHome;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

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
        LOG.info("POST /house\nBody:\n" + json);
        SmartHome home = homeService.getHomeById(houseId);
        if (home == null)
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        JsonRestParser parser = new JsonRestParser();
        Map<String,JsonParameter> parameters;
        try {
            parameters = parser.parseParameters(json);
        } catch (IOException e) {
            LOG.error("Error during parsing", e);
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        /*List<Map<String,JsonParameter>> parameters = parser.parseParameters(json);
        for (Map<String,JsonParameter> parameter : parameters) {
            HomeParam homeParam = new HomeParam();
            homeParam.setSmartHome(homeService.getHomeById(houseId));
            for (String parameterName : parameter.keySet()) {
                homeParam.setName(parameterName);
            }
            homeParam.setValue(parameter.get(homeParam.getName()).getValue());
            String type = parameter.get(homeParam.getName()).getType();
            if (type != "")
                homeParam.setDataType(dataTypeService.getDataTypeByName(type));
            else
                homeParam.setDataType(dataTypeService.getDataTypeByName("string"));
            homeService.saveParam(homeParam);
        }*/

        HomeParamTransformator paramTransformator = new HomeParamTransformator(dataTypeService, homeService);
        Iterator iterator = parameters.keySet().iterator();
        while (iterator.hasNext()) {
            String paramName = (String)iterator.next();
            parameters.get(paramName).setName(paramName);
            parameters.get(paramName).setSmartHomeId(houseId);
            HomeParam homeParam = paramTransformator.fromJsonEntity(parameters.get(paramName));
            try {
                homeService.saveParam(homeParam);
            } catch (Exception ex) {
                LOG.error("Error during saving of data", ex);
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
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
