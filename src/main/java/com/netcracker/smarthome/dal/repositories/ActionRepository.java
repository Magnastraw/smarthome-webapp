package com.netcracker.smarthome.dal.repositories;

import com.netcracker.smarthome.model.entities.Action;
import org.springframework.stereotype.Repository;

@Repository
public class ActionRepository extends EntityRepository<Action> {
    public ActionRepository() {
        super(Action.class);
    }
}
