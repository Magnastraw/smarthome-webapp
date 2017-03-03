package com.netcracker.smarthome.dao.repositories;

import com.netcracker.smarthome.model.entities.HomeParam;
import org.springframework.stereotype.Repository;

@Repository
public class HomeParamRepository extends EntityRepository<HomeParam> {
    public HomeParamRepository() {
        super(HomeParam.class);
    }
}
