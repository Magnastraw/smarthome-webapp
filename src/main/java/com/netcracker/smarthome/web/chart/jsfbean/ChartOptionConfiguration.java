package com.netcracker.smarthome.web.chart.jsfbean;

import com.netcracker.smarthome.model.entities.MetricSpec;
import com.netcracker.smarthome.model.entities.SmartObject;
import com.netcracker.smarthome.web.chart.highchartConfigurations.ChartConfig;

import java.util.List;


public class ChartOptionConfiguration {
    private ChartConfigInterface chartConfigInterface;

    public ChartConfigInterface getChartConfigInterface() {
        return chartConfigInterface;
    }

    public void setChartConfigInterface(ChartConfigInterface chartConfigInterface) {
        this.chartConfigInterface = chartConfigInterface;
    }

    public ChartConfig getConfig(List<MetricSpec> selectedMetricSpecs, List<SmartObject> selectedSmartObjects){
        return chartConfigInterface.configure(selectedMetricSpecs, selectedSmartObjects);
    }
}
