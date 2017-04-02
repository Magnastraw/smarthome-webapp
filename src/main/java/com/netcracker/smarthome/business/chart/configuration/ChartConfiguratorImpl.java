package com.netcracker.smarthome.business.chart.configuration;

import com.netcracker.smarthome.model.entities.MetricSpec;
import com.netcracker.smarthome.model.entities.SmartObject;

import java.util.List;


public class ChartConfiguratorImpl {
    private ChartConfigurator chartConfigurator;

    public ChartConfigurator getChartConfigurator() {
        return chartConfigurator;
    }

    public void setChartConfigurator(ChartConfigurator chartConfigurator) {
        this.chartConfigurator = chartConfigurator;
    }

    public ChartConfig getConfig(List<MetricSpec> selectedMetricSpecs, List<SmartObject> selectedSmartObjects){
        return chartConfigurator.configure(selectedMetricSpecs, selectedSmartObjects);
    }
}
