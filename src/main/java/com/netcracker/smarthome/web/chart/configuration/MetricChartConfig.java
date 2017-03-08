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

    public MetricChartConfig(SmartHome smartHome, int chartId, ChartService chartService,double refreshInterval) {
        super(smartHome, chartId, refreshInterval);
        this.chartService=chartService;
    }

    public Chart configure(List<MetricSpec> selectedMetricSpecs, List<SmartObject> selectedSmartObjects,List<SmartObject> selectedSubObject) {

        super.getChartConfig().getChartOptions().getTitle().put("text",selectedMetricSpecs.get(0).getSpecName());

        AxisConfig axisConfig = new AxisConfig();

        HashMap<String,String> title = new HashMap<String, String>();
        title.put("text",selectedMetricSpecs.get(0).getSpecName());
        axisConfig.setTitle(title);

        HashMap<String,String> labels = new HashMap<String, String>();
        labels.put("format","{value}"+chartService.getUnitBySpecId(selectedMetricSpecs.get(0).getSpecId()).getLabel());
        axisConfig.setLabels(labels);

        super.getChartConfig().getChartOptions().getyAxis().add(axisConfig);

        selectedSmartObjects=super.deleteParentObj(selectedSubObject,selectedSmartObjects);

        for (SmartObject smartObject:selectedSmartObjects) {
            SeriesConfig seriesConfig = new SeriesConfig();
            seriesConfig.setData("");
            seriesConfig.setName(smartObject.getName());
            seriesConfig.setType("line");
            super.getChartConfig().getChartOptions().getSeries().add(seriesConfig);
            super.getChartConfig().getRequestDataOptions().getObjectId().add(smartObject.getSmartObjectId());
        }
        super.getChartConfig().getRequestDataOptions().getMetricSpecId().add(selectedMetricSpecs.get(0).getSpecId());
        super.getChartConfig().getRequestDataOptions().setType('d');
        super.setInterval();

        return super.getChartConfig();
    }
}
