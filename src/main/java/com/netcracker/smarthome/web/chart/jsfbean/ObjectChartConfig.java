package com.netcracker.smarthome.web.chart.jsfbean;

import com.netcracker.smarthome.model.entities.MetricSpec;
import com.netcracker.smarthome.model.entities.SmartHome;
import com.netcracker.smarthome.model.entities.SmartObject;
import com.netcracker.smarthome.web.chart.highchartConfigurations.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ObjectChartConfig extends DefaultChart implements ChartConfigInterface {

    public ObjectChartConfig(SmartHome smartHome, int chartId, int height, int width) {
        super(smartHome, chartId, height, width);
    }

    public ChartConfig configure(List<MetricSpec> selectedMetricSpecs, List<SmartObject> selectedSmartObjects) {
        HashMap<String,String> chartTitle= new HashMap<String, String>();
        chartTitle.put("text",selectedSmartObjects.get(0).getName());
        super.getChartOptions().setTitle(chartTitle);

        ArrayList<AxisConfig> axisConfigs = new ArrayList<AxisConfig>();
        ArrayList<SeriesConfig> seriesConfigs = new ArrayList<SeriesConfig>();
        ArrayList<Long> objectIds = new ArrayList<Long>();
        ArrayList<Long> metricIds = new ArrayList<Long>();
        ArrayList<Long> subObjectIds = new ArrayList<Long>();

        for(MetricSpec metricSpec:selectedMetricSpecs){

            AxisConfig axisConfig = new AxisConfig();

            HashMap<String,String> title = new HashMap<String, String>();
            title.put("text",metricSpec.getSpecName());
            axisConfig.setTitle(title);

            HashMap<String,String> labels = new HashMap<String, String>();
            labels.put("format","{value}%");
            axisConfig.setLabels(labels);

            axisConfigs.add(axisConfig);

            SeriesConfig seriesConfig = new SeriesConfig();
            seriesConfig.setData("[]");
            seriesConfig.setName(metricSpec.getSpecName());
            seriesConfig.setType("line");
            seriesConfig.setyAxis(super.getyAxisNumber());
            seriesConfigs.add(seriesConfig);

            metricIds.add(metricSpec.getSpecId());
        }

        super.getRequestDataOptions().setMetricSpecId(metricIds);

        objectIds.add(selectedSmartObjects.get(0).getSmartObjectId());
        super.getRequestDataOptions().setObjectId(objectIds);

        subObjectIds.add(1L);
        super.getRequestDataOptions().setSubObjectId(subObjectIds);

        super.getRequestDataOptions().setType('o');

        super.getChartOptions().setyAxis(axisConfigs);
        super.getChartOptions().setSeries(seriesConfigs);
        super.getChartConfig().setChartOptions(super.getChartOptions());
        super.getChartConfig().setRequestDataOptions(super.getRequestDataOptions());
        return super.getChartConfig();
    }
}
