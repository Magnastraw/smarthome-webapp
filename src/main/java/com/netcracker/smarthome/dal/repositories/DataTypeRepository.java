package com.netcracker.smarthome.dal.repositories;

import com.netcracker.smarthome.model.entities.DataType;
import org.springframework.stereotype.Repository;

@Repository
public class DataTypeRepository extends EntityRepository<DataType> {
    public DataTypeRepository() {
        super(DataType.class);
    }
}
