package com.netcracker.smarthome.dal.repositories;

import com.netcracker.smarthome.model.entities.AlarmSpec;
import org.springframework.stereotype.Repository;

@Repository
public class AlarmSpecRepository extends EntityRepository<AlarmSpec> {
    public AlarmSpecRepository() {
        super(AlarmSpec.class);
    }
}
