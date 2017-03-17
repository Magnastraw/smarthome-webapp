package com.netcracker.smarthome.web.chart.configuration;

import com.netcracker.smarthome.business.chart.ChartService;
import com.netcracker.smarthome.model.entities.MetricSpec;
import com.netcracker.smarthome.model.entities.SmartHome;
import com.netcracker.smarthome.model.entities.SmartObject;
import com.netcracker.smarthome.web.chart.options.jsonfields.AxisConfig;
import com.netcracker.smarthome.web.chart.options.jsonfields.SeriesConfig;


import java.util.HashMap;
import java.util.List;

public class MetricChartConfig extends DefaultChartConfig implements ChartConfigInterface {
    private ChartService chartService;

    public MetricChartConfig(SmartHome smartHome, long chartId, ChartService chartService,double refreshInterval, String chartType,String chartInterval) {
        super(smartHome, chartId, refreshInterval, chartType,chartInterval);
        this.chartService=chartService;
    }

    public ChartOptionsInterface configure(List<MetricSpec> selectedMetricSpecs, List<SmartObject> selectedSmartObjects,List<SmartObject> selectedSubObject) {

        super.getChartConfig().getChartOptions().setChartTitle(selectedMetricSpecs.get(0).getSpecName());

        AxisConfig axisConfig = new AxisConfig();

        axisConfig.setTitle(selectedMetricSpecs.get(0).getSpecName());
        axisConfig.setLabel(chartService.getUnitBySpecId(selectedMetricSpecs.get(0).getSpecId()).getLabel());

        super.getChartConfig().getChartOptions().getyAxis().add(axisConfig);

        selectedSmartObjects=super.deleteParentObj(selectedSubObject,selectedSmartObjects);

        for (SmartObject smartObject:selectedSmartObjects) {
            SeriesConfig seriesConfig = new SeriesConfig();
            seriesConfig.setData("");
            seriesConfig.setName(smartObject.getName());
            seriesConfig.setType(super.getChartType());
            super.getChartConfig().getChartOptions().getSeries().add(seriesConfig);
            super.getChartConfig().getRequestDataOptions().getObjectId().add(smartObject.getSmartObjectId());
        }
        super.getChartConfig().getRequestDataOptions().getMetricSpecId().add(selectedMetricSpecs.get(0).getSpecId());
        super.setInterval();

        return super.getChartConfig();
    }
}
