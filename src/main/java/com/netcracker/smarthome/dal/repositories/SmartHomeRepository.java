package com.netcracker.smarthome.dal.repositories;

import com.netcracker.smarthome.model.entities.SmartHome;
import org.springframework.stereotype.Repository;

@Repository
public class SmartHomeRepository extends EntityRepository<SmartHome> {
    public SmartHomeRepository() {
        super(SmartHome.class);
    }
}
