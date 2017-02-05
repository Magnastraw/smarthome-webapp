package com.netcracker.smarthome.dal.repositories;

import com.netcracker.smarthome.model.entities.ConditionType;
import org.springframework.stereotype.Repository;

@Repository
public class ConditionTypeRepository extends EntityRepository<ConditionType> {
    public ConditionTypeRepository() {
        super(ConditionType.class);
    }
}
