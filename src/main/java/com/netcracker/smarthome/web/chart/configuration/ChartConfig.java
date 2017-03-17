package com.netcracker.smarthome.web.chart.configuration;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netcracker.smarthome.web.chart.options.ChartOptions;
import com.netcracker.smarthome.web.chart.options.RequestDataOptions;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;

public class ChartConfig implements ChartOptionsInterface{
    private static final Logger LOG = LoggerFactory.getLogger(ChartConfig.class);

    private String chartConfig;
    private String requestDataConfig;
    private long chartId;
    private ChartOptions chartOptions;
    private RequestDataOptions requestDataOptions;
    private double refreshInterval;
    private ObjectMapper objectMapper;
    VelocityContext vc;

    public ChartConfig(ChartOptions chartOptions, RequestDataOptions requestDataOptions) {
        this.chartOptions = chartOptions;
        this.requestDataOptions = requestDataOptions;
        this.objectMapper = new ObjectMapper();

        Velocity.init("C:/Users/hintr/Desktop/smarthome-webapp/src/main/resources/velocity.properties");

        vc = new VelocityContext();

    }

    public String getRequestDataConfig() throws JsonProcessingException {
        this.requestDataConfig = this.objectMapper.writeValueAsString(this.requestDataOptions);
        LOG.info("Request options:" + this.requestDataConfig);
        return this.requestDataConfig;
    }

    public void setRequestDataConfig(String requestDataConfig) {
        this.requestDataConfig = requestDataConfig;
    }

    public String getChartConfig() throws IOException {
        vc.put("chartOption", this.chartOptions);

        Template t = Velocity.getTemplate("velocityTemplates/default.vm", "utf-8");

        StringWriter sw = new StringWriter();

        t.merge(vc, sw);
        String temp = sw.toString();
        System.out.println("temp: "+temp);
        sw.close();
        LOG.info("Chart options:" + this.chartConfig);

      //  this.chartConfig = this.objectMapper.writeValueAsString(this.chartOptions);
        this.chartConfig = temp;
        LOG.info("Chart options:" + this.chartConfig);
        return this.chartConfig;
    }

    public void setChartConfig(String chartConfig) {
        this.chartConfig = chartConfig;
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

    public double getRefreshInterval() {
        return refreshInterval;
    }

    public void setRefreshInterval(double refreshInterval) {
        this.refreshInterval = refreshInterval;
    }
}
