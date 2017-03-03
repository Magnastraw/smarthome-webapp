package com.netcracker.smarthome.dao.repositories;

import com.netcracker.smarthome.model.entities.Condition;
import org.springframework.stereotype.Repository;

@Repository
public class ConditionRepository extends EntityRepository<Condition> {
    public ConditionRepository() {
        super(Condition.class);
    }
}
