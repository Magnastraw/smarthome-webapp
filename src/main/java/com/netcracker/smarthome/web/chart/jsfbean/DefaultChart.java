package com.netcracker.smarthome.web.chart.jsfbean;


import com.netcracker.smarthome.model.entities.SmartHome;
import com.netcracker.smarthome.web.chart.highchartConfigurations.ChartConfig;
import com.netcracker.smarthome.web.chart.highchartConfigurations.ChartOptions;
import com.netcracker.smarthome.web.chart.highchartConfigurations.RequestDataOptions;

import java.util.HashMap;


public class DefaultChart {

    private ChartConfig chartConfig;
    private ChartOptions chartOptions;
    private RequestDataOptions requestDataOptions;
    private int yAxisNumber;

    public DefaultChart(SmartHome smartHome, int chartId, int height, int width) {
        requestDataOptions = new RequestDataOptions();
        requestDataOptions.setSmartHomeId(smartHome.getSmartHomeId());

        chartConfig = new ChartConfig();
        chartConfig.setChartId(chartId);
        chartConfig.setHeight(height);
        chartConfig.setWidth(width);

        chartOptions = new ChartOptions();
        HashMap<String, String> chart = new HashMap<String, String>();
        chart.put("renderTo", "component" + chartId + "_chartDiv");
        chart.put("zoomType", "x");
        chart.put("type", "line");
        chartOptions.setChart(chart);

        HashMap<String, String> xAxis = new HashMap<String, String>();
        xAxis.put("type", "datetime");
        chartOptions.setxAxis(xAxis);

        HashMap<String, String> lang = new HashMap<String, String>();
        lang.put("fullscreenTooltip", "Fullscreen");
        chartOptions.setLang(lang);

        HashMap<String, Boolean> credits = new HashMap<String, Boolean>();
        credits.put("enabled", false);
        chartOptions.setCredits(credits);
    }

    public ChartConfig getChartConfig() {
        return chartConfig;
    }

    public void setChartConfig(ChartConfig chartConfig) {
        this.chartConfig = chartConfig;
    }

    public ChartOptions getChartOptions() {
        return chartOptions;
    }

    public void setChartOptions(ChartOptions chartOptions) {
        this.chartOptions = chartOptions;
    }

    public RequestDataOptions getRequestDataOptions() {
        return requestDataOptions;
    }

    public void setRequestDataOptions(RequestDataOptions requestDataOptions) {
        this.requestDataOptions = requestDataOptions;
    }

    public int getyAxisNumber() {
        return yAxisNumber++;
    }

    public void setyAxisNumber(int yAxisNumber) {
        this.yAxisNumber = yAxisNumber;
    }
}
