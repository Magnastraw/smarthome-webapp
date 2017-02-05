package com.netcracker.smarthome.dal.repositories;

import com.netcracker.smarthome.model.entities.ActionParam;
import org.springframework.stereotype.Repository;

@Repository
public class ActionParamRepository extends EntityRepository<ActionParam> {
    public ActionParamRepository() {
        super(ActionParam.class);
    }
}
