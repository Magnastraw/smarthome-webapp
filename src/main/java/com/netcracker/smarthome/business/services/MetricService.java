package com.netcracker.smarthome.business.services;

import com.netcracker.smarthome.dal.repositories.MetricHistoryRepository;
import com.netcracker.smarthome.dal.repositories.MetricRepository;
import com.netcracker.smarthome.dal.repositories.MetricSpecRepository;
import com.netcracker.smarthome.model.entities.Metric;
import com.netcracker.smarthome.model.entities.MetricHistory;
import com.netcracker.smarthome.model.entities.MetricSpec;
import com.netcracker.smarthome.model.entities.SmartObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@Service
public class MetricService {
    private final MetricRepository metricRepository;
    private final MetricHistoryRepository metricHistoryRepository;
    private final MetricSpecRepository metricSpecRepository;

    @Autowired
    public MetricService(MetricRepository metricRepository, MetricHistoryRepository metricHistoryRepository, MetricSpecRepository metricSpecRepository) {
        this.metricRepository = metricRepository;
        this.metricHistoryRepository = metricHistoryRepository;
        this.metricSpecRepository = metricSpecRepository;
    }

    @Transactional(readOnly = true)
    public MetricSpec getMetricSpecById(long specId) {
        return metricSpecRepository.get(specId);
    }

    @Transactional(readOnly = true)
    public Metric getMetric(long smartHomeId, long objectId,  Long subobjectId, long specId) {
        return metricRepository.getMetric(smartHomeId, objectId, subobjectId, specId);
    }

    @Transactional(readOnly = true)
    public Metric getMetric(SmartObject object, SmartObject subobject, MetricSpec spec) {
        return metricRepository.get(object, subobject, spec);
    }

    @Transactional
    public void saveMetric(Metric metric) {
        metricRepository.save(metric);
    }

    @Transactional
    public void saveMetricValue(MetricHistory metricHistory) {
        metricHistoryRepository.save(metricHistory);
    }

    @Transactional
    public Double getLastMetricValueByObject(long objectId, long specId) {
        return metricRepository.getLastMetricValueByObject(objectId, specId);
    }

    @Transactional
    public Double getLastMetricValueByPolicy(long policyId, long specId) {
        return metricRepository.getLastMetricValueByPolicy(policyId, specId);
    }
}
