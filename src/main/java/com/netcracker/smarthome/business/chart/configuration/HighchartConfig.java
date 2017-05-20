package com.netcracker.smarthome.business.chart.configuration;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netcracker.smarthome.business.chart.options.ChartOptions;
import com.netcracker.smarthome.business.chart.options.RequestDataOptions;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class HighchartConfig  {
    private long chartId;
    private ChartOptions chartOptions;
    private RequestDataOptions requestDataOptions;
    private long refreshInterval;

    public HighchartConfig(ChartOptions chartOptions, RequestDataOptions requestDataOptions) {
        this.chartOptions = chartOptions;
        this.requestDataOptions = requestDataOptions;
    }

    public long getChartId() {
        return chartId;
    }

    public void setChartId(long chartId) {
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

    public long getRefreshInterval() {
        return refreshInterval;
    }

    public void setRefreshInterval(long refreshInterval) {
        this.refreshInterval = refreshInterval;
    }
}
