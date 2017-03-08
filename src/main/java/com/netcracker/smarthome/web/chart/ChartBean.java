package com.netcracker.smarthome.web.chart;

import com.netcracker.smarthome.business.chart.ChartService;
import com.netcracker.smarthome.model.entities.*;
import com.netcracker.smarthome.web.chart.configuration.*;
import com.netcracker.smarthome.web.common.ContextUtils;
import com.netcracker.smarthome.web.home.CurrentUserHomesBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "chartBean")
@SessionScoped
public class ChartBean {

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
    private List<ChartConfig> chartConfigs;
    private ChartOptionsConfigurator chartOptionsConfigurator;

    @PostConstruct
    public void init() {

        chartOptionsConfigurator = new ChartOptionsConfigurator();
        chartConfigs = new ArrayList<ChartConfig>();
        selectedSubObjects = new ArrayList<SmartObject>();
        selectedMetricSpecs = new ArrayList<MetricSpec>();
        selectedSubObjects = new ArrayList<SmartObject>();

        //not supported?
//        smartObjectItems = new ArrayList<SelectItem>();
//        for(Catalog catalog:chartService.getObjectsCatalogs(smartHome.getSmartHomeId())){
//            SelectItemGroup group = new SelectItemGroup(catalog.getCatalogName());
//            List<SmartObject> smartObjectGroup = chartService.getObjectsByCatalogId(smartHome.getSmartHomeId(),catalog.getCatalogId());
//            System.out.println("smartobj"+smartObjectGroup.toString());
//            SelectItem[] selectItems = new SelectItem[smartObjectGroup.size()];
//            for(int i=0;i<smartObjectGroup.size();i++){
//                selectItems[i]=new SelectItem(smartObjectGroup.get(i),smartObjectGroup.get(i).getName());
//            }
//            group.setSelectItems(selectItems);
//            smartObjectItems.add(group);
//        }

    }

    public void addChart() {
        if (selectedMetricSpecs.size() == 1) {
            chartOptionsConfigurator.setChartConfigInterface(new MetricChartConfig(getCurrentHome(), ++chartId, chartService, refreshInterval));
            chartConfigs.add(chartOptionsConfigurator.getConfig(selectedMetricSpecs, selectedSmartObjects, selectedSubObjects));
        } else if (selectedSmartObjects.size() == 1) { //валидатор на проверку что в сабобжекте что то выбрали, если ничего не выбрали по умолчанию всё, и вообще валидторы на заполнение
            chartOptionsConfigurator.setChartConfigInterface(new ObjectChartConfig(getCurrentHome(), ++chartId, chartService, refreshInterval));
            chartConfigs.add(chartOptionsConfigurator.getConfig(selectedMetricSpecs, selectedSmartObjects, selectedSubObjects));
        } else {
            chartOptionsConfigurator.setChartConfigInterface(new MultiChartConfig(getCurrentHome(), ++chartId, getChartTitle(), chartService, refreshInterval));
            chartConfigs.add(chartOptionsConfigurator.getConfig(selectedMetricSpecs, selectedSmartObjects, selectedSubObjects));
        }
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


    public List<ChartConfig> getChartConfigs() {
        return chartConfigs;
    }

    public void setChartConfigs(List<ChartConfig> chartConfigs) {
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

}