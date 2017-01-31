package com.netcracker.smarthome.dal.repositories;

import com.netcracker.smarthome.model.entities.Metric;
import org.springframework.stereotype.Repository;

@Repository
public class MetricRepository extends EntityRepository<Metric> {
    public MetricRepository() {
        super(Metric.class);
    }
}
