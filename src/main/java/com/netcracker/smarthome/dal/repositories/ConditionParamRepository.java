package com.netcracker.smarthome.dal.repositories;

import com.netcracker.smarthome.model.entities.ConditionParam;
import org.springframework.stereotype.Repository;

@Repository
public class ConditionParamRepository extends EntityRepository<ConditionParam> {
    public ConditionParamRepository() {
        super(ConditionParam.class);
    }
}
