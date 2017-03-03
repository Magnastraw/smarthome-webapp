package com.netcracker.smarthome.dao.repositories;

import com.netcracker.smarthome.model.entities.ActionParam;
import org.springframework.stereotype.Repository;

@Repository
public class ActionParamRepository extends EntityRepository<ActionParam> {
    public ActionParamRepository() {
        super(ActionParam.class);
    }
}
