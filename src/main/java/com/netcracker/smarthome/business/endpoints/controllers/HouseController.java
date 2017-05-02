package com.netcracker.smarthome.business.endpoints.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netcracker.smarthome.business.HomeService;
import com.netcracker.smarthome.business.endpoints.JsonRestParser;
import com.netcracker.smarthome.business.endpoints.TaskManager;
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
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

@RestController
public class HouseController {
    private static final Logger LOG = LoggerFactory.getLogger(HouseController.class);
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private HomeService homeService;
    @Autowired
    private DataTypeService dataTypeService;
    @Autowired
    private TaskManager taskManager;

    @RequestMapping(value = "/house",
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<String> sendHomeParams(@RequestParam(value="houseId", required=true) long houseId,
                                         @RequestBody String json) {
        LOG.info("POST /house\nBody:\n" + json);
        SmartHome home = homeService.getHomeBySecretKey(houseId);
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

        HomeParamTransformator paramTransformator = new HomeParamTransformator(dataTypeService, homeService);
        Iterator iterator = parameters.keySet().iterator();
        while (iterator.hasNext()) {
            String paramName = (String) iterator.next();
            parameters.get(paramName).setName(paramName);
            parameters.get(paramName).setSmartHomeId(home.getSmartHomeId());
            HomeParam homeParam = paramTransformator.fromJsonEntity(parameters.get(paramName));

            HomeParam oldParam = homeService.getHomeParamByName(home.getSmartHomeId(), paramName);
            try {
                if (oldParam != null) {
                    homeParam.setParamId(oldParam.getParamId());
                    homeService.updateParam(homeParam);
                } else {
                    homeService.saveParam(homeParam);
                }
            } catch (Exception ex) {
                LOG.error("Error during saving of data", ex);
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        }
        String responseBody = new String();
        try {
            taskManager.addHomeTask(home.getSmartHomeId(), new HomeTask("GetInventory"));
            responseBody = mapper.writeValueAsString(taskManager.getTaskMap().get(home.getSmartHomeId()));
        } catch (JsonProcessingException ex) {
            LOG.error("Json to string", ex);
        }
        taskManager.getTaskMap().get(home.getSmartHomeId()).clear();
        return new ResponseEntity<String>(responseBody, HttpStatus.OK);
    }

    @RequestMapping(value = "/house",
            method = RequestMethod.GET,
            produces = "application/json")
    public ResponseEntity<String> getCommands(@RequestParam(value="houseId", required=true) String houseId) {
        SmartHome home = homeService.getHomeBySecretKey(houseId);
        if (home == null)
            return new ResponseEntity(HttpStatus.NOT_FOUND);

        String responseBody = new String();
        try {
            responseBody = mapper.writeValueAsString(taskManager.getTaskMap().get(home.getSmartHomeId()));
        } catch (JsonProcessingException ex) {
            LOG.error("Json to string", ex);
        }
        taskManager.getTaskMap().get(home.getSmartHomeId()).clear();
        return new ResponseEntity<String>(responseBody, HttpStatus.OK);
    }

}
