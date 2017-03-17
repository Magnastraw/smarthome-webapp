package com.netcracker.smarthome.web.chart.configuration;

import com.netcracker.smarthome.model.entities.MetricSpec;
import com.netcracker.smarthome.model.entities.SmartObject;
import com.netcracker.smarthome.web.chart.options.ChartOptions;

import java.util.List;

public interface ChartConfigInterface {
    ChartOptionsInterface configure(List<MetricSpec> selectedMetricSpecs, List<SmartObject> selectedSmartObjects, List<SmartObject> selectedSubObject);
}
