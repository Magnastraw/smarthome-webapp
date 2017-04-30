package com.netcracker.smarthome.business.services;

import com.netcracker.smarthome.dal.repositories.*;
import com.netcracker.smarthome.model.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ChartService {

    private final MetricHistoryRepository metricHistoryRepository;
    private final SmartObjectRepository smartObjectRepository;
    private final MetricSpecRepository metricSpecRepository;
    private final UnitRepository unitRepository;
    private final ChartRepository chartRepository;

    @Autowired
    public ChartService(MetricHistoryRepository metricHistoryRepository, SmartObjectRepository smartObjectRepository, MetricSpecRepository metricSpecRepository, UnitRepository unitRepository, ChartRepository chartRepository, DashBoardRepository dashBoardRepository) {
        this.metricHistoryRepository = metricHistoryRepository;
        this.smartObjectRepository = smartObjectRepository;
        this.metricSpecRepository = metricSpecRepository;
        this.unitRepository = unitRepository;
        this.chartRepository = chartRepository;
    }

    public List<SmartObject> getSmartObjectByIds(ArrayList<Long> smartObjectIds) {
        ArrayList<SmartObject> smartObjects = new ArrayList<SmartObject>();
        for (Long objectId : smartObjectIds) {
            smartObjects.add(smartObjectRepository.get(objectId));
        }
        return smartObjects;
    }

    public List<MetricSpec> getMetricSpec() {
        return metricSpecRepository.getAll();
    }

    public List<MetricSpec> getMetricsSpecByObjectId(long objectId) {
        return metricSpecRepository.getSpecByObjectId(objectId);
    }

    public Unit getUnitBySpecId(long specId) {
        return unitRepository.getUnitByMetricSpecId(specId);
    }

    public List<Chart> getCharts(long dashboardId) {
        return chartRepository.getChartByDashboardId(dashboardId);
    }

    public List<MetricHistory> getMetricsHistory(long homeId, long specId, long objectId, Timestamp selectDate) {
        return metricHistoryRepository.getMetricsHistory(homeId, specId, objectId, selectDate);
    }

    public List<MetricHistory> getLastMetricHistory(long homeId, long specId, long objectId) {
        return metricHistoryRepository.getLastMetricsHistory(homeId, specId, objectId);
    }


    public List<SmartObject> getSmartObjectByCatalogId(long homeId, long catalogId) {
        return smartObjectRepository.getObjectsByCatalogId(homeId, catalogId);
    }

    public List<SmartObject> getSubObjectByParentId(long objectId) {
        return smartObjectRepository.getSubobjectsByObjectId(objectId);
    }

    public List<MetricSpec> getMetricSpecByCatalogId(Catalog catalog) {
        return metricSpecRepository.getMetricSpecs(catalog);
    }

    public List<MetricSpec> getSupportedMetricSpecs(List<SmartObject> smartObjects){
        return metricSpecRepository.getSupportedSpecs(smartObjects);
    }

    @Transactional(readOnly = false)
    public long getChartId() {
        return chartRepository.getChartId();
    }

    @Transactional(readOnly = false)
    public void removeChart(long chartId) {
        chartRepository.delete(chartId);
    }

    @Transactional(readOnly = false)
    public void updateChart(Chart chart) {
        chartRepository.update(chart);
    }

    @Transactional(readOnly = false)
    public void addChart(String option, String request, double interval, Dashboard dashboard) {
        chartRepository.save(new Chart(option, request, BigDecimal.valueOf(interval), dashboard));
    }

}
