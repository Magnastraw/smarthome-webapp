package com.netcracker.smarthome.web.chart;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.netcracker.smarthome.business.chart.ChartService;
import com.netcracker.smarthome.model.entities.*;
import com.netcracker.smarthome.model.enums.ChartInterval;
import com.netcracker.smarthome.model.enums.ChartType;
import com.netcracker.smarthome.web.chart.configuration.*;
import com.netcracker.smarthome.web.common.ContextUtils;
import com.netcracker.smarthome.web.home.CurrentUserHomesBean;
import com.netcracker.smarthome.web.specs.AlarmSpecsBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.bean.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "chartBean")
@SessionScoped
public class ChartBean {
    private static final Logger LOG = LoggerFactory.getLogger(ChartBean.class);
    @ManagedProperty(value = "#{ChartService}")
    ChartService chartService;

    private int chartId;
    private double refreshInterval;
    private String chartTitle;
    private List<SmartObject> selectedSmartObjects;
    private List<SmartObject> smartObjects;
    private List<SmartObject> selectedSubObjects;
    private List<SmartObject> subObjects;
    private List<MetricSpec> selectedMetricSpecs;
    private List<MetricSpec> metricSpecs;
    private List<ChartOptionsInterface> chartConfigs;
    private List<Chart> chartConf;
    private ChartOptionsConfigurator chartOptionsConfigurator;
    private ChartType selectedChartType;
    private ChartType[] chartTypes;
    private ChartInterval selectedChartInterval;
    private ChartInterval[] chartIntervals;

    @PostConstruct
    public void init() {
        chartOptionsConfigurator = new ChartOptionsConfigurator();
        chartConfigs = new ArrayList<ChartOptionsInterface>();
        selectedSubObjects = new ArrayList<SmartObject>();
        selectedMetricSpecs = new ArrayList<MetricSpec>();
        selectedSubObjects = new ArrayList<SmartObject>();
        chartTypes = ChartType.values();
        chartIntervals = ChartInterval.values();

    }

    public boolean setCharts(Dashboard dashboard) {
        LOG.info("Current dashboard:" + dashboard.getDashboardId());
        chartConf = chartService.getChartOptionsEntity(dashboard.getDashboardId());
        return true;
    }

    public void addChart() throws IOException {
        long chartIdLong = chartService.getChartId() + 1;
        String chartType = selectedChartType.toString().toLowerCase();
        String chartInterval = selectedChartInterval.toString().toLowerCase();
        if (selectedMetricSpecs.size() == 1) {
            chartOptionsConfigurator.setChartConfigInterface(new MetricChartConfig(getCurrentHome(), chartIdLong, chartService, refreshInterval, chartType,chartInterval));
        } else if (selectedSmartObjects.size() == 1) {
            chartOptionsConfigurator.setChartConfigInterface(new ObjectChartConfig(getCurrentHome(), chartIdLong, chartService, refreshInterval, chartType,chartInterval));
        } else {
            chartOptionsConfigurator.setChartConfigInterface(new MultiChartConfig(getCurrentHome(), chartIdLong, getChartTitle(), chartService, refreshInterval, chartType,chartInterval));
        }
        ChartConfig chartConfig = (ChartConfig) chartOptionsConfigurator.getConfig(selectedMetricSpecs, selectedSmartObjects, selectedSubObjects);
        chartService.addChartOption(chartConfig.getChartConfig(), chartConfig.getRequestDataConfig(), chartConfig.getRefreshInterval(), getCurrentDashBoard());

        setCharts(getCurrentDashBoard());
        setDefaultCheckbox();
    }

    public void setDefaultCheckbox() {
        this.selectedSubObjects = new ArrayList<SmartObject>();
        this.selectedSmartObjects = new ArrayList<SmartObject>();
        this.selectedMetricSpecs = new ArrayList<MetricSpec>();
        this.subObjects = new ArrayList<SmartObject>();
        this.refreshInterval = 0.0;
        this.chartTitle = "";
    }

    public void removeChart(Chart chart) {
        chartService.removeChart(chart.getChartId());
        setCharts(getCurrentDashBoard());
    }

    public ChartInterval getSelectedChartInterval() {
        return selectedChartInterval;
    }

    public void setSelectedChartInterval(ChartInterval selectedChartInterval) {
        this.selectedChartInterval = selectedChartInterval;
    }

    public ChartInterval[] getChartIntervals() {
        return chartIntervals;
    }

    public void setChartIntervals(ChartInterval[] chartIntervals) {
        this.chartIntervals = chartIntervals;
    }

    public ChartType getSelectedChartType() {
        return selectedChartType;
    }

    public void setSelectedChartType(ChartType selectedChartType) {
        this.selectedChartType = selectedChartType;
    }

    public ChartType[] getChartTypes() {
        return chartTypes;
    }

    public void setChartTypes(ChartType[] chartTypes) {
        this.chartTypes = chartTypes;
    }

    public List<Chart> getChartConf() {
        return chartConf;
    }

    public void setChartConf(List<Chart> chartConf) {
        this.chartConf = chartConf;
    }

    public ChartService getChartService() {
        return chartService;
    }

    public void setChartService(ChartService chartService) {
        this.chartService = chartService;
    }

    public List<SmartObject> getSelectedSmartObjects() {
        return selectedSmartObjects;
    }

    public void setSelectedSmartObjects(List<SmartObject> selectedSmartObjects) {
        this.selectedSmartObjects = selectedSmartObjects;
    }

    public List<SmartObject> getSmartObjects() {
        return chartService.getSmartObjectByHomeId(getCurrentHome().getSmartHomeId());
    }

    public void setSmartObjects(List<SmartObject> smartObjects) {
        this.smartObjects = smartObjects;
    }

    public List<SmartObject> getSelectedSubObjects() {
        return selectedSubObjects;
    }

    public void setSelectedSubObjects(List<SmartObject> selectedSubObjects) {
        this.selectedSubObjects = selectedSubObjects;
    }

    public List<SmartObject> getSubObjects() {
        return this.subObjects;
    }

    public void setSubObjects(List<SmartObject> subObjects) {
        this.subObjects = subObjects;
    }

    public List<MetricSpec> getSelectedMetricSpecs() {
        return selectedMetricSpecs;
    }

    public void setSelectedMetricSpecs(List<MetricSpec> selectedMetricSpecs) {
        this.selectedMetricSpecs = selectedMetricSpecs;
    }

    public List<MetricSpec> getMetricSpecs() {
        return chartService.getMetricSpecByHomeId(getCurrentHome().getSmartHomeId());
    }

    public void setMetricSpecs(List<MetricSpec> metricSpecs) {
        this.metricSpecs = metricSpecs;
    }


    public List<ChartOptionsInterface> getChartConfigs() {
        return chartConfigs;
    }

    public void setChartConfigs(List<ChartOptionsInterface> chartConfigs) {
        this.chartConfigs = chartConfigs;
    }

    public double getRefreshInterval() {
        return refreshInterval;
    }

    public void setRefreshInterval(double refreshInterval) {
        this.refreshInterval = refreshInterval;
    }

    public String getChartTitle() {
        return chartTitle;
    }

    public void setChartTitle(String chartTitle) {
        this.chartTitle = chartTitle;
    }

    public void changeSelectObject() {
        ArrayList<Long> subObjectIds = new ArrayList<Long>();
        for (SmartObject object : selectedSmartObjects) {
            subObjectIds.add(object.getSmartObjectId());
        }
        subObjects = chartService.getSubObjectByParentIds(subObjectIds);
    }

    private SmartHome getCurrentHome() {
        return ((CurrentUserHomesBean) ContextUtils.getBean("currentUserHomesBean")).getCurrentHome();
    }

    public boolean isRendered() {
        return selectedSubObjects != null && selectedMetricSpecs != null && selectedSmartObjects != null && selectedSmartObjects.size() > 1 && selectedMetricSpecs.size() > 1;
    }

    public Dashboard getCurrentDashBoard() {
        return ((DashboardBean) ContextUtils.getBean("dashboardBean")).getCurrentDashboard();
    }

}