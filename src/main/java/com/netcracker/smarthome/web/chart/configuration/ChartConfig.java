package com.netcracker.smarthome.web.chart.configuration;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netcracker.smarthome.web.chart.options.ChartOptions;
import com.netcracker.smarthome.web.chart.options.RequestDataOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChartConfig {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultChartConfig.class);

    private String chartConfig;
    private String requestDataConfig;
    private int chartId;
    private ChartOptions chartOptions;
    private RequestDataOptions requestDataOptions;
    private double refreshInterval;
    private ObjectMapper objectMapper;

    public ChartConfig(ChartOptions chartOptions, RequestDataOptions requestDataOptions) {
        this.chartOptions = chartOptions;
        this.requestDataOptions = requestDataOptions;
        this.objectMapper = new ObjectMapper();
    }

    public String getRequestDataConfig() throws JsonProcessingException {
        this.requestDataConfig = this.objectMapper.writeValueAsString(this.requestDataOptions);
        LOG.info("Request options:" + this.requestDataConfig);
        return this.requestDataConfig;
    }

    public void setRequestDataConfig(String requestDataConfig) {
        this.requestDataConfig = requestDataConfig;
    }

    public String getChartConfig() throws JsonProcessingException {
        this.chartConfig = this.objectMapper.writeValueAsString(this.chartOptions);
        LOG.info("Chart options:" + this.chartConfig);
        return this.chartConfig;
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

    public double getRefreshInterval() {
        return refreshInterval;
    }

    public void setRefreshInterval(double refreshInterval) {
        this.refreshInterval = refreshInterval;
    }
}
