package com.netcracker.smarthome.business.endpoints.transformators;

import com.netcracker.smarthome.business.endpoints.jsonentities.JsonInventoryObject;
import com.netcracker.smarthome.business.endpoints.services.SmartObjectService;
import com.netcracker.smarthome.model.entities.SmartHome;
import com.netcracker.smarthome.model.entities.SmartObject;
import org.springframework.beans.factory.annotation.Autowired;

public class InventoryTransformator  implements ITransformator<SmartObject, JsonInventoryObject, SmartHome> {

    @Autowired
    private final SmartObjectService smartObjectService;

    public InventoryTransformator(SmartObjectService smartObjectService) {
        this.smartObjectService = smartObjectService;
    }

    public SmartObject fromJsonEntity(JsonInventoryObject jsonEntity, SmartHome smartHome) {
        SmartObject smartObject = new SmartObject();
        smartObject.setSmartHome(smartHome);
        smartObject.setExternalKey(jsonEntity.getObjectId());
        smartObject.setName(jsonEntity.getObjectName());
        smartObject.setObjectType(smartObjectService.getObjectTypeByName(jsonEntity.getObjectType()));
        if (jsonEntity.getParentId() != 0)
            smartObject.setParentObject(smartObjectService.getObjectByExternalKey(smartHome.getSmartHomeId(), jsonEntity.getParentId()));
        smartObject.setCatalog(smartObjectService.getRootCatalog(smartHome.getSmartHomeId()));
        return smartObject;
    }

}
