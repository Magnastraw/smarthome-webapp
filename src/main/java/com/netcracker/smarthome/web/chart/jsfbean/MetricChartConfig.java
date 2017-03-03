package com.netcracker.smarthome.web.chart.jsfbean;


import com.netcracker.smarthome.model.entities.MetricSpec;
import com.netcracker.smarthome.model.entities.SmartHome;
import com.netcracker.smarthome.model.entities.SmartObject;
import com.netcracker.smarthome.web.chart.highchartConfigurations.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MetricChartConfig extends DefaultChart implements ChartConfigInterface {

    public MetricChartConfig(SmartHome smartHome, int chartId, int height, int width) {
        super(smartHome, chartId, height, width);
    }


    public ChartConfig configure(List<MetricSpec> selectedMetricSpecs, List<SmartObject> selectedSmartObjects) {
        HashMap<String,String> chartTitle= new HashMap<String, String>();

        chartTitle.put("text",selectedMetricSpecs.get(0).getSpecName());
        super.getChartOptions().setTitle(chartTitle);

        ArrayList<AxisConfig> axisConfigs = new ArrayList<AxisConfig>();

        AxisConfig axisConfig = new AxisConfig();

        HashMap<String,String> title = new HashMap<String, String>();
        title.put("text",selectedMetricSpecs.get(0).getSpecName());
        axisConfig.setTitle(title);

        HashMap<String,String> labels = new HashMap<String, String>();
        labels.put("format","{value}°C");
        axisConfig.setLabels(labels);

        axisConfigs.add(axisConfig);

        super.getChartOptions().setyAxis(axisConfigs);

        ArrayList<SeriesConfig> seriesConfigs = new ArrayList<SeriesConfig>();
        ArrayList<Long> objectIds = new ArrayList<Long>();
        ArrayList<Long> metricIds = new ArrayList<Long>();
        ArrayList<Long> subObjectIds = new ArrayList<Long>();

        for (SmartObject smartObject:selectedSmartObjects) {
            SeriesConfig seriesConfig = new SeriesConfig();
            seriesConfig.setData("[]");
            seriesConfig.setName(smartObject.getName());
            seriesConfig.setType("line");
            seriesConfigs.add(seriesConfig);

            objectIds.add(smartObject.getSmartObjectId());
        }
        super.getRequestDataOptions().setObjectId(objectIds);

        metricIds.add(selectedMetricSpecs.get(0).getSpecId());
        super.getRequestDataOptions().setMetricSpecId(metricIds);

        subObjectIds.add(1L);
        super.getRequestDataOptions().setSubObjectId(subObjectIds);

        super.getRequestDataOptions().setType('m');

        super.getChartOptions().setSeries(seriesConfigs);

        super.getChartOptions().setyAxis(axisConfigs);
        super.getChartOptions().setSeries(seriesConfigs);
        super.getChartConfig().setChartOptions(super.getChartOptions());
        super.getChartConfig().setRequestDataOptions(super.getRequestDataOptions());

        return super.getChartConfig();
    }
}
