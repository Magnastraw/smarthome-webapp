package com.netcracker.smarthome.business;

import com.netcracker.smarthome.dal.repositories.DataTypeRepository;
import com.netcracker.smarthome.dal.repositories.HomeParamRepository;
import com.netcracker.smarthome.dal.repositories.SmartHomeRepository;
import com.netcracker.smarthome.model.entities.DataType;
import com.netcracker.smarthome.model.entities.HomeParam;
import com.netcracker.smarthome.model.entities.SmartHome;
import com.netcracker.smarthome.model.entities.User;
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

    @Autowired
    public HomeService(SmartHomeRepository homeRepository, HomeParamRepository paramRepository, DataTypeRepository dataTypeRepository) {
        this.homeRepository = homeRepository;
        this.paramRepository = paramRepository;
        this.dataTypeRepository = dataTypeRepository;
    }

    @Transactional
    public void createHome(SmartHome home) {
        setDefaultParams(home);
        homeRepository.save(home);
    }

    private void setDefaultParams(SmartHome home) {
        ArrayList<HomeParam> params = new ArrayList<HomeParam>();
        params.add(new HomeParam("Address", "address", home, dataTypeRepository.getByType(Type.STRING)));
        params.add(new HomeParam("Link", "http://www.outlink.com", home, dataTypeRepository.getByType(Type.LINK)));
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
}
