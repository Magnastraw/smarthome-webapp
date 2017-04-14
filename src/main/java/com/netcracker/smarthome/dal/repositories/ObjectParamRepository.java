package com.netcracker.smarthome.dal.repositories;

import com.netcracker.smarthome.model.entities.ObjectParam;
import org.springframework.stereotype.Repository;

@Repository
public class ObjectParamRepository extends EntityRepository<ObjectParam> {
    public ObjectParamRepository() {
        super(ObjectParam.class);
    }
}
