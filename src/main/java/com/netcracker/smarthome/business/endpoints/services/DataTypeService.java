package com.netcracker.smarthome.business.endpoints.services;

import com.netcracker.smarthome.dal.repositories.DataTypeRepository;
import com.netcracker.smarthome.model.entities.DataType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DataTypeService {

    @Autowired
    private DataTypeRepository dataTypeRepository;

    @Transactional(readOnly = true)
    public DataType getDataTypeByName(String name) {
        return dataTypeRepository.getByName(name.toLowerCase());
    }
}
