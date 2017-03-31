package com.netcracker.smarthome.business;

import com.netcracker.smarthome.dal.repositories.MetricRepository;
import com.netcracker.smarthome.model.entities.MetricSpec;
import com.netcracker.smarthome.model.entities.Policy;
import com.netcracker.smarthome.model.entities.SmartObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MetricService {
    private final MetricRepository metricRepository;

    @Autowired
    public MetricService(MetricRepository metricRepository) {
        this.metricRepository = metricRepository;
    }

    @Transactional
    public Double getLastMetricValue(SmartObject object, MetricSpec spec) {
        return metricRepository.getLastMetricValue(object, spec);
    }

    @Transactional
    public Double getLastMetricValue(Policy policy, MetricSpec spec) {
        return metricRepository.getLastMetricValue(policy, spec);
    }
}
