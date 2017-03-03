package com.netcracker.smarthome.dao.repositories;

import com.netcracker.smarthome.model.entities.ConditionParam;
import org.springframework.stereotype.Repository;

@Repository
public class ConditionParamRepository extends EntityRepository<ConditionParam> {
    public ConditionParamRepository() {
        super(ConditionParam.class);
    }
}
