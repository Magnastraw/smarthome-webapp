package com.netcracker.smarthome.dao.repositories;

import com.netcracker.smarthome.model.entities.Unit;
import org.springframework.stereotype.Repository;

@Repository
public class UnitRepository extends EntityRepository<Unit> {
    public UnitRepository() {
        super(Unit.class);
    }
}
