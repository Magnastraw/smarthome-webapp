package com.netcracker.smarthome.business.chart;

import com.netcracker.smarthome.dal.repositories.*;
import com.netcracker.smarthome.model.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service("ChartService")
@Transactional(readOnly = true)
public class ChartService {

    @Autowired
    private MetricHistoryRepository metricHistoryRepository;

    @Autowired
    private SmartObjectRepository smartObjectRepository;

    @Autowired
    private MetricSpecRepository metricSpecRepository;

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private ChartRepository chartRepository;

    @Autowired
    private DashBoardRepository dashBoardRepository;

    public List<SmartObject> getSmartObjectByIds(ArrayList<Long> smartObjectIds) {
        ArrayList<SmartObject> smartObjects = new ArrayList<SmartObject>();
        for (Long objectId : smartObjectIds) {
            smartObjects.add(smartObjectRepository.get(objectId));
        }
        return smartObjects;
    }

    public List<SmartObject> getSubObjectByParentIds(ArrayList<Long> parentObjectIds) {
        return smartObjectRepository.getSubObjectByObjectIds(parentObjectIds);
    }

    public List<MetricSpec> getMetricSpecByIds(ArrayList<Long> metricSpecIds) {
        ArrayList<MetricSpec> metricSpecObjects = new ArrayList<MetricSpec>();
        for (Long objectId : metricSpecIds) {
            metricSpecObjects.add(metricSpecRepository.get(objectId));
        }
        return metricSpecObjects;
    }

    public List<MetricSpec> getMetricSpec() {
        return metricSpecRepository.getAll();
    }


    public List<SmartObject> getSmartObjectByHomeId(long homeId) {
        return smartObjectRepository.getObjectByHomeId(homeId);
    }

    public List<MetricSpec> getMetricSpecByHomeId(long specId) {
        return metricSpecRepository.getMetricSpecByHomeId(specId);
    }

    public List<Object[]> getMetricsHistoryBySpecIdObjectId(long homeId, long specId, long objectId, int rownum, int seriesSize) {
        return metricHistoryRepository.getMetricsHistoryBySpecIdObjectId(homeId, specId, objectId, rownum, seriesSize);
    }

    public List<MetricSpec> getMetricsSpecByObjectId(long objectId) {
        return metricSpecRepository.getSpecByObjectId(objectId);
    }

    public Unit getUnitBySpecId(long specId) {
        return unitRepository.getUnitByMetricSpecId(specId);
    }

    @Transactional(readOnly = false)
    public void addChartOption(String option, String request, double interval, Dashboard dashboard) {
        chartRepository.save(new Chart(option, request, BigDecimal.valueOf(interval), dashboard));
    }

    public List<Chart> getChartOptionsEntity(long dashboardId) {
        return chartRepository.getChartByDashboardId(dashboardId);
    }

    @Transactional(readOnly=false)
    public long getChartId() {
        return chartRepository.getChartId();
    }

    public List<Dashboard> getDashboards(long homeId){
        return dashBoardRepository.getDahboardsByHomeId(homeId);
    }

    @Transactional(readOnly = false)
    public void addDashboard(Dashboard dashboard){
        dashBoardRepository.save(dashboard);
    }

    @Transactional(readOnly = false)
    public void deleteDashboard(Dashboard dashboard){
        dashBoardRepository.delete(dashboard.getDashboardId());
    }

    @Transactional
    public long getWidgetAmount(Dashboard dashboard){
        return dashBoardRepository.getWidgetsAmount(dashboard.getDashboardId());
    }

    @Transactional(readOnly = false)
    public void updateDashboard(Dashboard dashboard){
        dashBoardRepository.update(dashboard);
    }

    @Transactional
    public List<Object[]>  getRangedMetricHistory(long homeId, long specId, long objectId, int rownum, int seriesSize){
        return metricHistoryRepository.getRangedMetricsHistoryBySpecIdObjectId(homeId, specId, objectId, rownum, seriesSize);
    }

    @Transactional(readOnly = false)
    public void removeChart(long chartId){
        chartRepository.delete(chartId);
    }
}
