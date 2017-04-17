package com.netcracker.smarthome.business;

import com.netcracker.smarthome.dal.repositories.MetricRepository;
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
    public Double getLastMetricValueByObject(long objectId, long specId) {
        return metricRepository.getLastMetricValueByObject(objectId, specId);
    }

    @Transactional
    public Double getLastMetricValueByPolicy(long policyId, long specId) {
        return metricRepository.getLastMetricValueByObject(policyId, specId);
    }
}
