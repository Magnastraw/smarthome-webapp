package com.netcracker.smarthome.business.endpoints.transformators;

import com.netcracker.smarthome.business.endpoints.jsonentities.JsonParameter;
import com.netcracker.smarthome.business.endpoints.services.DataTypeService;
import com.netcracker.smarthome.business.endpoints.services.SmartObjectService;
import com.netcracker.smarthome.model.entities.ObjectParam;
import org.springframework.beans.factory.annotation.Autowired;

public class ObjectParamTransformator implements ITransformator<ObjectParam, JsonParameter> {

    private final DataTypeService dataTypeService;
    private final SmartObjectService smartObjectService;

    @Autowired
    public ObjectParamTransformator(DataTypeService dataTypeService, SmartObjectService smartObjectService) {
        this.dataTypeService = dataTypeService;
        this.smartObjectService = smartObjectService;
    }

    public ObjectParam fromJsonEntity(JsonParameter jsonEntity) {
        ObjectParam objectParam = new ObjectParam();
        objectParam.setName(jsonEntity.getName());
        objectParam.setValue(jsonEntity.getValue());
        if (jsonEntity.getType() != "")
            objectParam.setDataType(dataTypeService.getDataTypeByName(jsonEntity.getType()));
        else
            objectParam.setDataType(dataTypeService.getDataTypeByName("string"));
        objectParam.setObject(smartObjectService.getObjectById(jsonEntity.getSmartObjectId()));
        return objectParam;
    }
}
