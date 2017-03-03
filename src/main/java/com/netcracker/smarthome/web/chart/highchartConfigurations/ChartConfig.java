package com.netcracker.smarthome.web.chart.highchartConfigurations;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ChartConfig {
    private String chartConfig;
    private String requestDataConfig;
    private int chartId;
    private int height;
    private int width;
    private ChartOptions chartOptions;
    private RequestDataOptions requestDataOptions;

    public String getRequestDataConfig() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(requestDataOptions);
    }

    public void setRequestDataConfig(String requestDataConfig) {
        this.requestDataConfig = requestDataConfig;
    }

    public String getChartConfig() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(chartOptions);
    }

    public void setChartConfig(String chartConfig) {
        this.chartConfig = chartConfig;
    }

    public int getChartId() {
        return chartId;
    }

    public void setChartId(int chartId) {
        this.chartId = chartId;
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

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }


}
