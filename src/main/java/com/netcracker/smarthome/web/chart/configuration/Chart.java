package com.netcracker.smarthome.web.chart.configuration;


import com.fasterxml.jackson.core.JsonProcessingException;

public interface Chart {
    String getRequestDataConfig()throws JsonProcessingException;
    String getChartConfig() throws JsonProcessingException;
    int getChartId();
    double getRefreshInterval();
}
