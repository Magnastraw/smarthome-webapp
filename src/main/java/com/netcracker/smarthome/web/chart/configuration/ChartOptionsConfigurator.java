package com.netcracker.smarthome.web.chart.configuration;

import com.netcracker.smarthome.model.entities.MetricSpec;
import com.netcracker.smarthome.model.entities.SmartObject;

import java.util.List;


public class ChartOptionsConfigurator {
    private ChartConfigInterface chartConfigInterface;

    public ChartConfigInterface getChartConfigInterface() {
        return chartConfigInterface;
    }

    public void setChartConfigInterface(ChartConfigInterface chartConfigInterface) {
        this.chartConfigInterface = chartConfigInterface;
    }

    public ChartOptionsInterface getConfig(List<MetricSpec> selectedMetricSpecs, List<SmartObject> selectedSmartObjects,List<SmartObject> selectedSubObject){
        return chartConfigInterface.configure(selectedMetricSpecs, selectedSmartObjects, selectedSubObject);
    }
}
