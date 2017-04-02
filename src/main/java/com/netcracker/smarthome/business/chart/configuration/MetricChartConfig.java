package com.netcracker.smarthome.business.chart.configuration;

import com.netcracker.smarthome.business.chart.ChartService;
import com.netcracker.smarthome.model.entities.MetricSpec;
import com.netcracker.smarthome.model.entities.SmartHome;
import com.netcracker.smarthome.model.entities.SmartObject;
import com.netcracker.smarthome.business.chart.options.jsonfields.AxisConfig;
import com.netcracker.smarthome.business.chart.options.jsonfields.SeriesConfig;


import java.util.List;

public class MetricChartConfig extends DefaultChartConfig implements ChartConfigurator {
    private ChartService chartService;

    public MetricChartConfig(SmartHome smartHome, long chartId, ChartService chartService, double refreshInterval, String chartType, String chartInterval) {
        super(smartHome, chartId, refreshInterval, chartType, chartInterval);
        this.chartService = chartService;
    }

    public ChartConfig configure(List<MetricSpec> selectedMetricSpecs, List<SmartObject> selectedSmartObjects) {

        super.getChartConfigIml().getChartOptions().setChartTitle(selectedMetricSpecs.get(0).getSpecName());
        AxisConfig axisConfig = new AxisConfig();
        axisConfig.setTitle(selectedMetricSpecs.get(0).getSpecName());
        axisConfig.setLabel(chartService.getUnitBySpecId(selectedMetricSpecs.get(0).getSpecId()).getLabel());
        super.getChartConfigIml().getChartOptions().getyAxis().add(axisConfig);

        for (SmartObject smartObject : selectedSmartObjects) {
            SeriesConfig seriesConfig = new SeriesConfig();
            seriesConfig.setData("");
            seriesConfig.setName(smartObject.getName());
            seriesConfig.setType(super.getChartType());
            super.getChartConfigIml().getChartOptions().getSeries().add(seriesConfig);
            super.getChartConfigIml().getRequestDataOptions().getObjectId().add(smartObject.getSmartObjectId());
        }
        super.getChartConfigIml().getRequestDataOptions().getMetricSpecId().add(selectedMetricSpecs.get(0).getSpecId());

        return super.getChartConfigIml();
    }
}
