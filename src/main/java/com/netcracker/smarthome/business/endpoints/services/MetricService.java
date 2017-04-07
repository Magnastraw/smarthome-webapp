package com.netcracker.smarthome.business.endpoints.services;

import com.netcracker.smarthome.dal.repositories.MetricHistoryRepository;
import com.netcracker.smarthome.dal.repositories.MetricRepository;
import com.netcracker.smarthome.dal.repositories.MetricSpecRepository;
import com.netcracker.smarthome.model.entities.Metric;
import com.netcracker.smarthome.model.entities.MetricHistory;
import com.netcracker.smarthome.model.entities.MetricSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MetricService {
    @Autowired
    private MetricRepository metricRepository;

    @Autowired
    private MetricHistoryRepository metricHistoryRepository;

    @Autowired
    private MetricSpecRepository metricSpecRepository;

    @Transactional(readOnly = true)
    public MetricSpec getMetricSpecById(long specId) {
        return metricSpecRepository.get(specId);
    }

    @Transactional(readOnly = true)
    public Metric getMetric(long smartHomeId, long smartObjectId, long specId) {
        return metricRepository.getMetric(smartHomeId, smartObjectId, specId);
    }

    @Transactional
    public void saveMetric(Metric metric) {
        metricRepository.save(metric);
    }

    @Transactional
    public void saveMetricValue(MetricHistory metricHistory) {
        metricHistoryRepository.save(metricHistory);
    }
}
