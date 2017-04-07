package com.netcracker.smarthome.business.specs;

import com.netcracker.smarthome.dal.repositories.MetricSpecRepository;
import com.netcracker.smarthome.model.entities.Catalog;
import com.netcracker.smarthome.model.entities.MetricSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MetricSpecService {
    private final MetricSpecRepository metricSpecRepository;

    @Autowired
    public MetricSpecService (MetricSpecRepository metricSpecRepository) {
        this.metricSpecRepository = metricSpecRepository;
    }

    @Transactional
    public void deleteMetricSpec(Object primaryKey) {
        metricSpecRepository.delete(primaryKey);
    }

    @Transactional(readOnly = true)
    public List<MetricSpec> getMetricSpecs(Catalog catalog) {
        return metricSpecRepository.getMetricSpecs(catalog);
    }

    @Transactional
    public void createMetricSpec(MetricSpec metricSpec) {
        metricSpecRepository.save(metricSpec);
    }

    @Transactional
    public MetricSpec updateMetricSpec(MetricSpec metricSpec) {
        return metricSpecRepository.update(metricSpec);
    }

    @Transactional(readOnly = true)
    public boolean checkMetricName(String specName, long catalogId) {
        return this.metricSpecRepository.checkMetricSpecName(specName, catalogId);
    }

}
