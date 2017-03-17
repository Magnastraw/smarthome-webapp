package com.netcracker.smarthome.web.chart.configuration;


import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;

public interface ChartOptionsInterface {
    String getRequestDataConfig()throws JsonProcessingException;
    String getChartConfig() throws IOException;
    long getChartId();
    double getRefreshInterval();
}
