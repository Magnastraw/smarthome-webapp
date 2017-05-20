package com.netcracker.smarthome.business.services;

import com.netcracker.smarthome.dal.repositories.CatalogRepository;
import com.netcracker.smarthome.dal.repositories.DataTypeRepository;
import com.netcracker.smarthome.dal.repositories.HomeParamRepository;
import com.netcracker.smarthome.dal.repositories.SmartHomeRepository;
import com.netcracker.smarthome.model.entities.*;
import com.netcracker.smarthome.model.enums.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class HomeService {
    private final SmartHomeRepository homeRepository;
    private final HomeParamRepository paramRepository;
    private final DataTypeRepository dataTypeRepository;
    private final CatalogRepository catalogRepository;

    @Autowired
    public HomeService(SmartHomeRepository homeRepository, HomeParamRepository paramRepository, DataTypeRepository dataTypeRepository, CatalogRepository catalogRepository) {
        this.homeRepository = homeRepository;
        this.paramRepository = paramRepository;
        this.dataTypeRepository = dataTypeRepository;
        this.catalogRepository = catalogRepository;
    }

    @Transactional
    public void createHome(SmartHome home) {
        //setDefaultParams(home);
        homeRepository.save(home);
        createRootCatalogs(home);
    }

    private void setDefaultParams(SmartHome home) {
        ArrayList<HomeParam> params = new ArrayList<HomeParam>();
        params.add(new HomeParam("Address", "address", home, dataTypeRepository.getByType(Type.STRING)));
        params.add(new HomeParam("Home page", "http://localhost:8083/", home, dataTypeRepository.getByType(Type.LINK)));
        home.setHomeParams(params);
    }

    @Transactional(readOnly = true)
    public List<SmartHome> getHomesList(User user) {
        return homeRepository.getHomesByUser(user);
    }

    @Transactional(readOnly = true)
    public List<HomeParam> getHomeParams(SmartHome home) {
        return homeRepository.getParams(home);
    }

    @Transactional(readOnly = true)
    public List<DataType> getDataTypes() {
        return dataTypeRepository.getAll();
    }

    @Transactional
    public HomeParam updateParam(HomeParam param) {
        return paramRepository.update(param);
    }

    @Transactional
    public void deleteParam(HomeParam param) {
        paramRepository.delete(param.getParamId());
    }

    @Transactional
    public SmartHome updateHome(SmartHome home) {
        return homeRepository.update(home);
    }

    @Transactional
    public void createRootCatalogs(SmartHome home) {
        catalogRepository.save(new Catalog("metricSpecsRootCatalog", null, home));
        catalogRepository.save(new Catalog("alarmSpecsRootCatalog", null, home));
        catalogRepository.save(new Catalog("policiesRootCatalog", null, home));
        catalogRepository.save(new Catalog("objectsRootCatalog", null, home));
    }

    @Transactional(readOnly = true)
    public SmartHome getHomeById(long smartHomeId) {
        return homeRepository.get(smartHomeId);
    }

    @Transactional
    public void saveParam(HomeParam param) {
        paramRepository.save(param);
    }

    @Transactional(readOnly = true)
    public HomeParam getHomeParamByName(long smartHomeId, String paramName) {
        return paramRepository.getHomeParamByName(smartHomeId, paramName.toLowerCase());
    }

    @Transactional(readOnly = true)
    public SmartHome getHomeBySecretKey(String secretKey) {
        return paramRepository.getHomeBySecretKey(secretKey);
    }

    @Transactional(readOnly = true)
    public List<SmartHome> getSmartHomes() {
        return homeRepository.getAll();
    }
}
