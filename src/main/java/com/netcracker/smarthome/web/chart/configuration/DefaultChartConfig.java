package com.netcracker.smarthome.web.chart.configuration;


import com.netcracker.smarthome.business.chart.ChartService;
import com.netcracker.smarthome.model.entities.MetricSpec;
import com.netcracker.smarthome.model.entities.SmartHome;
import com.netcracker.smarthome.model.entities.SmartObject;
import com.netcracker.smarthome.web.chart.options.ChartOptions;
import com.netcracker.smarthome.web.chart.options.RequestDataOptions;
import com.netcracker.smarthome.web.chart.options.jsonfields.AxisConfig;
import com.netcracker.smarthome.web.chart.options.jsonfields.SeriesConfig;
import com.netcracker.smarthome.web.chart.options.jsonfields.YAxisNumber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DefaultChartConfig {

    private ChartConfig chartConfig;
    private int yAxisNumber;
    private final double TO_MILLISECONDS_CONVERT = 60000;

    public DefaultChartConfig(SmartHome smartHome, int chartId, double refreshInterval) {

        chartConfig = new ChartConfig(new ChartOptions(new HashMap<String, String>(), new ArrayList<AxisConfig>(), new ArrayList<SeriesConfig>()), new RequestDataOptions(new ArrayList<Long>(), new ArrayList<Long>()));
        chartConfig.setChartId(chartId);
        chartConfig.setRefreshInterval(refreshInterval * TO_MILLISECONDS_CONVERT);

        chartConfig.getRequestDataOptions().setSmartHomeId(smartHome.getSmartHomeId());

        HashMap<String, String> chart = new HashMap<String, String>();
        chart.put("renderTo", "component" + chartId + "_chartDiv");
        chart.put("zoomType", "x");
        chart.put("type", "line");
        chartConfig.getChartOptions().setChart(chart);

        HashMap<String, String> xAxis = new HashMap<String, String>();
        xAxis.put("type", "datetime");
        chartConfig.getChartOptions().setxAxis(xAxis);

        HashMap<String, String> lang = new HashMap<String, String>();
        lang.put("fullscreenTooltip", "Fullscreen");
        chartConfig.getChartOptions().setLang(lang);

        HashMap<String, Boolean> credits = new HashMap<String, Boolean>();
        credits.put("enabled", false);
        chartConfig.getChartOptions().setCredits(credits);
    }

    public ChartConfig getChartConfig() {
        return chartConfig;
    }

    public void setChartConfig(ChartConfig chartConfig) {
        this.chartConfig = chartConfig;
    }

    public int getyAxisNumber() {
        return yAxisNumber;
    }

    public void setyAxisNumber(int yAxisNumber) {
        this.yAxisNumber = yAxisNumber;
    }

    public void setInterval() {
        if (chartConfig.getRefreshInterval() == 0 || chartConfig.getRefreshInterval() > 5000) {
            System.out.println("Size:"+chartConfig.getRefreshInterval());
            chartConfig.getRequestDataOptions().setSeriesSize(100);
        } else {
            chartConfig.getRequestDataOptions().setSeriesSize(1);
        }
    }

    public List<SmartObject> deleteParentObj(List<SmartObject> selectedSubObject, List<SmartObject> selectedSmartObjects) {
        for (SmartObject smartObject : selectedSubObject) {
            for (int i = 0; i < selectedSmartObjects.size(); i++) {
                if (smartObject.getParentObject().getSmartObjectId() == selectedSmartObjects.get(i).getSmartObjectId()) {
                    selectedSmartObjects.remove(i);
                }
            }
            selectedSmartObjects.add(smartObject);
        }
        return selectedSmartObjects;
    }

    public ArrayList<YAxisNumber> setAxisOptions(List<MetricSpec> selectedMetricSpecs, ChartService chartService) {
        ArrayList<YAxisNumber> yAxisNumbers = new ArrayList<YAxisNumber>();
        for (MetricSpec metricSpec : selectedMetricSpecs) {

            AxisConfig axisConfig = new AxisConfig();
            YAxisNumber yAxisNumber = new YAxisNumber();

            yAxisNumber.setNumber(this.yAxisNumber++);
            yAxisNumber.setSpecId(metricSpec.getSpecId());
            yAxisNumbers.add(yAxisNumber);

            HashMap<String, String> title = new HashMap<String, String>();
            title.put("text", metricSpec.getSpecName());
            axisConfig.setTitle(title);

            HashMap<String, String> labels = new HashMap<String, String>();
            labels.put("format", "{value}" + chartService.getUnitBySpecId(metricSpec.getSpecId()).getLabel());
            axisConfig.setLabels(labels);

            chartConfig.getChartOptions().getyAxis().add(axisConfig);

            chartConfig.getRequestDataOptions().getMetricSpecId().add(metricSpec.getSpecId());
        }
        return yAxisNumbers;
    }
}
