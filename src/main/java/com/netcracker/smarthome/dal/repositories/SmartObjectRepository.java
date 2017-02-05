package com.netcracker.smarthome.dal.repositories;

import com.netcracker.smarthome.model.entities.SmartObject;
import org.springframework.stereotype.Repository;

@Repository
public class SmartObjectRepository extends EntityRepository<SmartObject> {
    public SmartObjectRepository() {
        super(SmartObject.class);
    }
}
