package com.netcracker.smarthome.business;

import com.netcracker.smarthome.dal.repositories.CatalogRepository;
import com.netcracker.smarthome.dal.repositories.DataTypeRepository;
import com.netcracker.smarthome.dal.repositories.HomeParamRepository;
import com.netcracker.smarthome.dal.repositories.SmartHomeRepository;
import com.netcracker.smarthome.model.entities.*;
import com.netcracker.smarthome.model.enums.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        homeRepository.save(home);
        createDataTypesIfNotExists();
        setDefaultParams(home);
        createRootCatalogs(home);
    }

    @Transactional
    private void setDefaultParams(SmartHome home) {
        paramRepository.save(new HomeParam("Address", "address", home, dataTypeRepository.getByType(Type.STRING)));
        paramRepository.save(new HomeParam("Link", "http://www.outlink.com", home, dataTypeRepository.getByType(Type.LINK)));
    }

    @Transactional
    private void createDataTypesIfNotExists() {
        if (dataTypeRepository.getByType(Type.STRING) == null)
            dataTypeRepository.save(new DataType("String", Type.STRING));
        if (dataTypeRepository.getByType(Type.LINK) == null)
            dataTypeRepository.save(new DataType("Link", Type.LINK));
    }

    @Transactional(readOnly = true)
    public List<SmartHome> getHomesList(User user) {
        List<SmartHome> homes = homeRepository.getHomesByUser(user);
        for (SmartHome home : homes) {
            home.setHomeParams(getHomeParams(home));
        }
        return homes;
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
}
