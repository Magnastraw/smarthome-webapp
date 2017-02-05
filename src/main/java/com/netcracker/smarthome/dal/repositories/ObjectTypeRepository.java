package com.netcracker.smarthome.dal.repositories;

import com.netcracker.smarthome.model.entities.ObjectType;
import org.springframework.stereotype.Repository;

@Repository
public class ObjectTypeRepository extends EntityRepository<ObjectType> {
    public ObjectTypeRepository() {
        super(ObjectType.class);
    }
}
