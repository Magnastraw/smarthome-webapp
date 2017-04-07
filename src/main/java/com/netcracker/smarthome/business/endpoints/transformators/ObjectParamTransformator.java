package com.netcracker.smarthome.business.endpoints.transformators;

import com.netcracker.smarthome.business.endpoints.jsonentities.JsonParameter;
import com.netcracker.smarthome.business.endpoints.services.DataTypeService;
import com.netcracker.smarthome.model.entities.ObjectParam;
import com.netcracker.smarthome.model.entities.SmartObject;
import org.springframework.beans.factory.annotation.Autowired;

public class ObjectParamTransformator implements ITransformator<ObjectParam, JsonParameter, SmartObject> {

    @Autowired
    private final DataTypeService dataTypeService;

    public ObjectParamTransformator(DataTypeService dataTypeService) {
        this.dataTypeService = dataTypeService;
    }

    public ObjectParam fromJsonEntity(JsonParameter jsonEntity, SmartObject smartObject) {
        ObjectParam objectParam = new ObjectParam();
        objectParam.setName(jsonEntity.getName());
        objectParam.setValue(jsonEntity.getValue());
        if (jsonEntity.getType() != "")
            objectParam.setDataType(dataTypeService.getDataTypeByName(jsonEntity.getType()));
        else
            objectParam.setDataType(dataTypeService.getDataTypeByName("string"));
        objectParam.setObject(smartObject);
        return objectParam;
    }
}
