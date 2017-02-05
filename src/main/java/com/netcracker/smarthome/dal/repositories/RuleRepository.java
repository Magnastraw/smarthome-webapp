package com.netcracker.smarthome.dal.repositories;

import com.netcracker.smarthome.model.entities.Rule;
import org.springframework.stereotype.Repository;

@Repository
public class RuleRepository extends EntityRepository<Rule> {
    public RuleRepository() {
        super(Rule.class);
    }
}
