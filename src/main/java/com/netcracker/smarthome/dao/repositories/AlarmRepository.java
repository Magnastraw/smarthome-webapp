package com.netcracker.smarthome.dao.repositories;

import com.netcracker.smarthome.model.entities.Alarm;
import org.springframework.stereotype.Repository;

@Repository
public class AlarmRepository extends EntityRepository<Alarm> {
    public AlarmRepository() {
        super(Alarm.class);
    }
}
