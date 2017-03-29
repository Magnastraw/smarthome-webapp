package com.netcracker.smarthome.business.policy.services;

import com.netcracker.smarthome.dal.repositories.SmartObjectRepository;
import com.netcracker.smarthome.model.entities.SmartObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SmartObjectService {
    private final SmartObjectRepository smartObjectRepository;

    @Autowired
    public SmartObjectService(SmartObjectRepository smartObjectRepository){
        this.smartObjectRepository = smartObjectRepository;
    }

    @Transactional
    public SmartObject getObjectById(long smartObjectId) {
        return smartObjectRepository.getObjectById(smartObjectId);
    }
}
