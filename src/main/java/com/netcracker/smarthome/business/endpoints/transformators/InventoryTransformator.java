package com.netcracker.smarthome.business.endpoints.transformators;

import com.netcracker.smarthome.business.HomeService;
import com.netcracker.smarthome.business.endpoints.jsonentities.JsonInventoryObject;
import com.netcracker.smarthome.business.endpoints.services.SmartObjectService;
import com.netcracker.smarthome.model.entities.SmartObject;
import org.springframework.beans.factory.annotation.Autowired;

public class InventoryTransformator  implements ITransformator<SmartObject, JsonInventoryObject> {
    private final SmartObjectService smartObjectService;
    private final HomeService homeService;

    @Autowired
    public InventoryTransformator(SmartObjectService smartObjectService, HomeService homeService) {
        this.smartObjectService = smartObjectService;
        this.homeService = homeService;
    }

    public SmartObject fromJsonEntity(JsonInventoryObject jsonEntity) {
        SmartObject smartObject = new SmartObject();
        smartObject.setSmartHome(homeService.getHomeById(jsonEntity.getSmartHomeId()));
        smartObject.setExternalKey(jsonEntity.getObjectId());
        smartObject.setName(jsonEntity.getObjectName());
        smartObject.setDescription(jsonEntity.getObjectDescription());
        smartObject.setObjectType(smartObjectService.getObjectTypeByName(jsonEntity.getObjectType().toLowerCase()));
        if (jsonEntity.getParentId() != 0)
            smartObject.setParentObject(smartObjectService.getObjectByExternalKey(jsonEntity.getSmartHomeId(), jsonEntity.getParentId()));
        smartObject.setCatalog(smartObjectService.getRootCatalog(jsonEntity.getSmartHomeId()));
        return smartObject;
    }

    public JsonInventoryObject toJsonEntity(SmartObject Entity) {
        throw new UnsupportedOperationException("Not supported");
    }

}
