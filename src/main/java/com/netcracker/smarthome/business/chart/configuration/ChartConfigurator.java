package com.netcracker.smarthome.business.chart.configuration;

import com.netcracker.smarthome.model.entities.MetricSpec;
import com.netcracker.smarthome.model.entities.SmartObject;

import java.util.List;

public interface ChartConfigurator {
    HighchartConfig configure(List<MetricSpec> selectedMetricSpecs, List<SmartObject> selectedSmartObjects);
}
