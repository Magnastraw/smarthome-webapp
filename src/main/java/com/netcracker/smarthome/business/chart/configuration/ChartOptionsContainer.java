package com.netcracker.smarthome.business.chart.configuration;


public class ChartOptionsContainer {
    private String jsonChartConfig;
    private String jsonRequestOptions;
    private long refreshInterval;

    public String getJsonChartConfig() {
        return jsonChartConfig;
    }

    public void setJsonChartConfig(String jsonChartConfig) {
        this.jsonChartConfig = jsonChartConfig;
    }

    public String getJsonRequestOptions() {
        return jsonRequestOptions;
    }

    public void setJsonRequestOptions(String jsonRequestOptions) {
        this.jsonRequestOptions = jsonRequestOptions;
    }

    public long getRefreshInterval() {
        return refreshInterval;
    }

    public void setRefreshInterval(long refreshInterval) {
        this.refreshInterval = refreshInterval;
    }
}
