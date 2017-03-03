package com.netcracker.smarthome.web.chart.jsfbean;

import com.netcracker.smarthome.model.entities.MetricSpec;
import com.netcracker.smarthome.model.entities.SmartObject;
import com.netcracker.smarthome.web.chart.highchartConfigurations.ChartConfig;

import java.util.List;

public interface ChartConfigInterface {
    ChartConfig configure(List<MetricSpec> selectedMetricSpecs, List<SmartObject> selectedSmartObjects);

}
