package com.netcracker.smarthome.business.endpoints.transformators;

import com.netcracker.smarthome.business.endpoints.jsonentities.JsonParameter;
import com.netcracker.smarthome.business.endpoints.services.DataTypeService;
import com.netcracker.smarthome.model.entities.HomeParam;
import com.netcracker.smarthome.model.entities.SmartHome;
import org.springframework.beans.factory.annotation.Autowired;

public class HomeParamTransformator implements ITransformator<HomeParam, JsonParameter, SmartHome> {

    @Autowired
    private final DataTypeService dataTypeService;

    public HomeParamTransformator(DataTypeService dataTypeService) {
        this.dataTypeService = dataTypeService;
    }

    public HomeParam fromJsonEntity(JsonParameter jsonEntity, SmartHome smartHome) {
        HomeParam homeParam = new HomeParam();
        homeParam.setSmartHome(smartHome);
        homeParam.setName(jsonEntity.getName());
        homeParam.setValue(jsonEntity.getValue());
        if (jsonEntity.getType() != "")
            homeParam.setDataType(dataTypeService.getDataTypeByName(jsonEntity.getType()));
        else
            homeParam.setDataType(dataTypeService.getDataTypeByName("string"));
        return homeParam;
    }
}
