package com.netcracker.smarthome.dao.repositories;

import com.netcracker.smarthome.model.entities.MetricHistory;
import org.springframework.stereotype.Repository;

@Repository
public class MetricHistoryRepository extends EntityRepository<MetricHistory> {
    public MetricHistoryRepository() {
        super(MetricHistory.class);
    }
}
