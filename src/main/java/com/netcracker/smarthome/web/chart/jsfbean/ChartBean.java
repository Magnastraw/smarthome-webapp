package com.netcracker.smarthome.web.chart.jsfbean;

import com.netcracker.smarthome.business.chart.ChartService;
import com.netcracker.smarthome.model.entities.*;
import com.netcracker.smarthome.web.chart.highchartConfigurations.*;
import com.netcracker.smarthome.web.common.ContextUtils;
import com.netcracker.smarthome.web.home.CurrentUserHomesBean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.util.ArrayList;
import java.util.List;
//
@ManagedBean(name = "chartBean")
@SessionScoped
public class ChartBean {
    @ManagedProperty(value = "#{ChartService}")
    ChartService chartService;

    private int height;
    private int width;
    private int chartId;
    private int refreshInterval;
    private SmartHome smartHome;
    private List<SmartObject> selectedSmartObjects;
    private List<SmartObject> smartObjects;
    private List<MetricSpec> selectedMetricSpecs;
    private List<MetricSpec> metricSpecs;
    private List<ChartConfig> chartConfigs;

    @PostConstruct
    public void init(){
        chartConfigs = new ArrayList<ChartConfig>();
    }

    public void addChart(){
        ChartOptionConfiguration chartConfiguration = new ChartOptionConfiguration();
        smartHome = ((CurrentUserHomesBean) ContextUtils.getBean("currentUserHomesBean")).getCurrentHome();
        if(selectedMetricSpecs.size()<=selectedSmartObjects.size()) {
            chartConfiguration.setChartConfigInterface(new MetricChartConfig(smartHome,++chartId,getHeight(),getWidth()));
            chartConfigs.add(chartConfiguration.getConfig(selectedMetricSpecs,selectedSmartObjects));
        } else if(selectedMetricSpecs.size()>selectedSmartObjects.size()) {
            chartConfiguration.setChartConfigInterface(new ObjectChartConfig(smartHome,++chartId,getHeight(),getWidth()));
            chartConfigs.add(chartConfiguration.getConfig(selectedMetricSpecs,selectedSmartObjects));
        }
    }

    public ChartService getChartService() {
        return chartService;
    }

    public void setChartService(ChartService chartService) {
        this.chartService = chartService;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public List<SmartObject> getSelectedSmartObjects() {
        return selectedSmartObjects;
    }

    public void setSelectedSmartObjects(List<SmartObject> selectedSmartObjects) {
        this.selectedSmartObjects = selectedSmartObjects;
    }

    public List<SmartObject> getSmartObjects() {
        smartHome=((CurrentUserHomesBean) ContextUtils.getBean("currentUserHomesBean")).getCurrentHome();
        return chartService.getSmartObjectByHomeId(smartHome.getSmartHomeId());
    }

    public void setSmartObjects(List<SmartObject> smartObjects) {
        this.smartObjects = smartObjects;
    }

    public List<MetricSpec> getSelectedMetricSpecs() {
        return selectedMetricSpecs;
    }

    public void setSelectedMetricSpecs(List<MetricSpec> selectedMetricSpecs) {
        this.selectedMetricSpecs = selectedMetricSpecs;
    }

    public List<MetricSpec> getMetricSpecs() {
        smartHome=((CurrentUserHomesBean) ContextUtils.getBean("currentUserHomesBean")).getCurrentHome();
        return chartService.getMetricSpecByHomeId(smartHome.getSmartHomeId());
    }

    public void setMetricSpecs(List<MetricSpec> metricSpecs) {
        this.metricSpecs = metricSpecs;
    }


    public List<ChartConfig> getChartConfigs() {
        return chartConfigs;
    }

    public void setChartConfigs(List<ChartConfig> chartConfigs) {
        this.chartConfigs = chartConfigs;
    }

    public int getRefreshInterval() {
        return refreshInterval;
    }

    public void setRefreshInterval(int refreshInterval) {
        this.refreshInterval = refreshInterval;
    }
}
