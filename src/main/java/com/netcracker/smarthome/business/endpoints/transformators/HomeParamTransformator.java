package com.netcracker.smarthome.business.endpoints.transformators;

import com.netcracker.smarthome.business.HomeService;
import com.netcracker.smarthome.business.endpoints.jsonentities.JsonParameter;
import com.netcracker.smarthome.business.endpoints.services.DataTypeService;
import com.netcracker.smarthome.model.entities.HomeParam;
import org.springframework.beans.factory.annotation.Autowired;

public class HomeParamTransformator implements ITransformator<HomeParam, JsonParameter> {

    @Autowired
    private final DataTypeService dataTypeService;
    @Autowired
    private final HomeService homeService;

    public HomeParamTransformator(DataTypeService dataTypeService, HomeService homeService) {
        this.dataTypeService = dataTypeService;
        this.homeService = homeService;
    }

    public HomeParam fromJsonEntity(JsonParameter jsonEntity) {
        HomeParam homeParam = new HomeParam();
        homeParam.setSmartHome(homeService.getHomeById(jsonEntity.getSmartHomeId()));
        homeParam.setName(jsonEntity.getName());
        homeParam.setValue(jsonEntity.getValue());
        if (jsonEntity.getType() != "")
            homeParam.setDataType(dataTypeService.getDataTypeByName(jsonEntity.getType()));
        else
            homeParam.setDataType(dataTypeService.getDataTypeByName("string"));
        return homeParam;
    }
}
