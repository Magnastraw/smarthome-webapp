package com.netcracker.smarthome.web.chart.configuration;

import com.netcracker.smarthome.model.entities.MetricSpec;
import com.netcracker.smarthome.model.entities.SmartObject;

import java.util.List;

public interface ChartConfigInterface {
    ChartConfig configure(List<MetricSpec> selectedMetricSpecs, List<SmartObject> selectedSmartObjects, List<SmartObject> selectedSubObject);
}
