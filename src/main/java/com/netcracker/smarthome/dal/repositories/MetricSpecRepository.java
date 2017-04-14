package com.netcracker.smarthome.dal.repositories;

import com.netcracker.smarthome.model.entities.MetricSpec;
import org.springframework.stereotype.Repository;

@Repository
public class MetricSpecRepository extends EntityRepository<MetricSpec> {
    public MetricSpecRepository() {
        super(MetricSpec.class);
    }
}
