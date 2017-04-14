package com.netcracker.smarthome.dal.repositories;

import com.netcracker.smarthome.model.entities.MetricHistory;
import org.springframework.stereotype.Repository;

@Repository
public class MetricHistoryRepository extends EntityRepository<MetricHistory> {
    public MetricHistoryRepository() {
        super(MetricHistory.class);
    }
}
