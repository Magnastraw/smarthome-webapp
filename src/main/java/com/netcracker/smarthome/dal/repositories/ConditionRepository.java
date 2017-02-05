package com.netcracker.smarthome.dal.repositories;

import com.netcracker.smarthome.model.entities.Condition;
import org.springframework.stereotype.Repository;

@Repository
public class ConditionRepository extends EntityRepository<Condition> {
    public ConditionRepository() {
        super(Condition.class);
    }
}
