package com.netcracker.smarthome.dao.repositories;

import com.netcracker.smarthome.model.entities.MetricSpec;
import org.springframework.stereotype.Repository;

@Repository
public class MetricSpecRepository extends EntityRepository<MetricSpec> {
    public MetricSpecRepository() {
        super(MetricSpec.class);
    }
}
