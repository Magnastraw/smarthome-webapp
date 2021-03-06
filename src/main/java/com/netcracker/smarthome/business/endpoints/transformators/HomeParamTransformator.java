package com.netcracker.smarthome.business.endpoints.transformators;

import com.netcracker.smarthome.business.services.HomeService;
import com.netcracker.smarthome.business.endpoints.jsonentities.JsonParameter;
import com.netcracker.smarthome.business.services.DataTypeService;
import com.netcracker.smarthome.model.entities.HomeParam;
import org.springframework.beans.factory.annotation.Autowired;

public class HomeParamTransformator implements ITransformator<HomeParam, JsonParameter> {
    private final DataTypeService dataTypeService;
    private final HomeService homeService;

    @Autowired
    public HomeParamTransformator(DataTypeService dataTypeService, HomeService homeService) {
        this.dataTypeService = dataTypeService;
        this.homeService = homeService;
    }

    public HomeParam fromJsonEntity(JsonParameter jsonEntity) {
        HomeParam homeParam = new HomeParam();
        homeParam.setSmartHome(homeService.getHomeById(jsonEntity.getSmartHomeId()));
        homeParam.setName(jsonEntity.getName());
        homeParam.setValue(jsonEntity.getValue());
        String type = jsonEntity.getType();
        if (type != "")
            homeParam.setDataType(dataTypeService.getDataTypeByName(type));
        else
            homeParam.setDataType(dataTypeService.getDataTypeByName("string"));
        return homeParam;
    }

    public JsonParameter toJsonEntity(HomeParam Entity) {
        throw new UnsupportedOperationException("Not supported");
    }
}
