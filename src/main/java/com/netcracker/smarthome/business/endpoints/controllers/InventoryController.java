package com.netcracker.smarthome.business.endpoints.controllers;

import com.netcracker.smarthome.business.endpoints.JsonRestParser;
import com.netcracker.smarthome.business.endpoints.TaskManager;
import com.netcracker.smarthome.business.endpoints.jsonentities.JsonInventoryObject;
import com.netcracker.smarthome.business.endpoints.transformators.InventoryTransformator;
import com.netcracker.smarthome.business.endpoints.transformators.ObjectParamTransformator;
import com.netcracker.smarthome.business.policy.core.PolicyEngine;
import com.netcracker.smarthome.business.services.DataTypeService;
import com.netcracker.smarthome.business.services.HomeService;
import com.netcracker.smarthome.business.services.PolicyService;
import com.netcracker.smarthome.business.services.SmartObjectService;
import com.netcracker.smarthome.model.entities.ObjectParam;
import com.netcracker.smarthome.model.entities.Policy;
import com.netcracker.smarthome.model.entities.SmartHome;
import com.netcracker.smarthome.model.entities.SmartObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
public class InventoryController {
    private static final Logger LOG = LoggerFactory.getLogger(InventoryController.class);

    @Autowired
    private SmartObjectService smartObjectService;

    @Autowired
    private HomeService homeService;

    @Autowired
    private DataTypeService dataTypeService;

    @Autowired
    private TaskManager taskManager;

    @Autowired
    private PolicyEngine policyEngine;

    @Autowired
    private PolicyService policyService;

    @RequestMapping(value = "/inventories",
            method = RequestMethod.POST,
            consumes = "application/json")
    public ResponseEntity sendInventories(@RequestParam(value = "houseId", required = true) String houseId,
                                          @RequestBody String json) {
        LOG.info("POST /inventories\nBody:\n" + json);
        SmartHome home = homeService.getHomeBySecretKey(houseId);
        if (home == null)
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        JsonRestParser parser = new JsonRestParser();
        List<JsonInventoryObject> objects;
        try {
            objects = parser.parseInventory(json);
        } catch (IOException e) {
            LOG.error("Error during parsing", e);
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        if (objects.size() == 0)
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        InventoryTransformator inventoryTransformator = new InventoryTransformator(smartObjectService, homeService);
        ObjectParamTransformator paramTransformator = new ObjectParamTransformator(dataTypeService, smartObjectService);
        boolean firstRequest = true;
        List<SmartObject> newObjects = new ArrayList<>();
        for (JsonInventoryObject object : objects) {
            object.setSmartHomeId(home.getSmartHomeId());
            SmartObject smartObject = inventoryTransformator.fromJsonEntity(object);
            SmartObject oldObject = smartObjectService.getObjectByExternalKey(home.getSmartHomeId(), smartObject.getExternalKey());
            try {
                if (oldObject != null) {
                    firstRequest = false;
                    smartObject.setSmartObjectId(oldObject.getSmartObjectId());
                    smartObjectService.updateInventory(smartObject);
                } else {
                    smartObjectService.saveInventory(smartObject);
                    newObjects.add(smartObject);
                }
                Iterator iterator = object.getParameters().keySet().iterator();
                while (iterator.hasNext()) {
                    String paramName = (String) iterator.next();
                    object.getParameters().get(paramName).setSmartObjectId(smartObject.getSmartObjectId());
                    object.getParameters().get(paramName).setName(paramName);
                    ObjectParam objectParam = paramTransformator.fromJsonEntity(object.getParameters().get(paramName));
                    ObjectParam oldParam = smartObjectService.getObjectParamByName(smartObject.getSmartObjectId(), objectParam.getName());
                    if (oldParam != null) {
                        objectParam.setParamId(oldParam.getParamId());
                        smartObjectService.updateObjectParam(objectParam);
                    } else {
                        smartObjectService.saveObjectParam(objectParam);
                    }
                }
            } catch (Exception ex) {
                LOG.error("Error during saving of data", ex);
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        }
        if (firstRequest)
            policyEngine.initialize();
        else if (!newObjects.isEmpty()) {
            List<Policy> activePolicies = policyService.getActiveInlinePolicies(newObjects);
            for (Policy policy : activePolicies) {
                policyEngine.onSaveOrUpdate(policy);
            }
        }
        taskManager.addUpdateEvent(home.getSmartHomeId(), "updateInventory");
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/inventories/{objectId}",
            method = RequestMethod.PUT,
            consumes = "application/json")
    public ResponseEntity updateInventory(@PathVariable(value = "objectId", required = true) long objectId,
                                          @RequestParam(value = "houseId", required = true) String houseId,
                                          @RequestBody String json) {
        LOG.info("PUT /inventories\nBody:\n" + json);
        SmartHome home = homeService.getHomeBySecretKey(houseId);
        if (home == null)
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        JsonRestParser parser = new JsonRestParser();
        JsonInventoryObject jsonObject;
        try {
            jsonObject = parser.parseInventoryObject(json);
        } catch (IOException e) {
            LOG.error("Error during parsing", e);
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        InventoryTransformator inventoryTransformator = new InventoryTransformator(smartObjectService, homeService);
        ObjectParamTransformator paramTransformator = new ObjectParamTransformator(dataTypeService, smartObjectService);
        jsonObject.setSmartHomeId(home.getSmartHomeId());
        SmartObject smartObject = inventoryTransformator.fromJsonEntity(jsonObject);
        SmartObject oldObject = smartObjectService.getObjectByExternalKey(home.getSmartHomeId(), objectId);
        smartObject.setSmartObjectId(oldObject.getSmartObjectId());
        try {
            smartObjectService.updateInventory(smartObject);
            Iterator iterator = jsonObject.getParameters().keySet().iterator();
            while (iterator.hasNext()) {
                String paramName = (String) iterator.next();
                jsonObject.getParameters().get(paramName).setName(paramName);
                jsonObject.getParameters().get(paramName).setSmartObjectId(smartObject.getSmartObjectId());
                ObjectParam objectParam = paramTransformator.fromJsonEntity(jsonObject.getParameters().get(paramName));

                ObjectParam oldParam = smartObjectService.getObjectParamByName(smartObject.getSmartObjectId(), objectParam.getName());
                if (oldParam != null) {
                    objectParam.setParamId(oldParam.getParamId());
                    smartObjectService.updateObjectParam(objectParam);
                } else {
                    smartObjectService.saveObjectParam(objectParam);
                }
            }
            taskManager.addUpdateEvent(home.getSmartHomeId(), "updateInventory");
        } catch (Exception ex) {
            LOG.error("Error during saving of data", ex);
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/inventories/{objectId}",
            method = RequestMethod.DELETE)
    public ResponseEntity deleteInventory(@PathVariable(value = "objectId", required = true) long objectId,
                                          @RequestParam(value = "houseId", required = true) String houseId) {
        LOG.info("DELETE /inventories\nId:\n" + objectId);
        SmartHome home = homeService.getHomeBySecretKey(houseId);
        if (home == null)
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        try {
            SmartObject objectToDelete = smartObjectService.getObjectByExternalKey(home.getSmartHomeId(), objectId);
            List<Policy> policiesToReload = policyService.getActivePoliciesByObject(objectToDelete);
            smartObjectService.deleteObject(objectToDelete.getSmartObjectId());
            for (Policy policy : policiesToReload) {
                policyEngine.onSaveOrUpdate(policy);
            }
            taskManager.addUpdateEvent(home.getSmartHomeId(), "updateInventory");
        } catch (Exception ex) {
            LOG.error("Error during deleting of object", ex);
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/updateInventory",
            method = RequestMethod.GET)
    public Boolean updateInventory(@RequestParam(value = "houseId", required = true) long houseId) {
        return taskManager.getUpdateMap().get(houseId) != null && taskManager.getUpdateMap().get(houseId).remove("updateInventory");
    }
}
