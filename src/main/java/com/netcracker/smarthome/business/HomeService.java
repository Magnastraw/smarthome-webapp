package com.netcracker.smarthome.business;

import com.netcracker.smarthome.dal.repositories.DataTypeRepository;
import com.netcracker.smarthome.dal.repositories.HomeParamRepository;
import com.netcracker.smarthome.dal.repositories.SmartHomeRepository;
import com.netcracker.smarthome.model.entities.HomeParam;
import com.netcracker.smarthome.model.entities.SmartHome;
import com.netcracker.smarthome.model.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void createDefaultHome(User user) {
        SmartHome home = new SmartHome("Default home", "", user);
        homeRepository.save(home);
        setDefaultParams(home);
    }

    @Transactional
    private void setDefaultParams(SmartHome home) {
        paramRepository.save(new HomeParam("City", "", home, dataTypeRepository.get((long) 1)));
        paramRepository.save(new HomeParam("Street", "", home, dataTypeRepository.get((long) 1)));
    }
}
