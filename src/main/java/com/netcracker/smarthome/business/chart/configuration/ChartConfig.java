package com.netcracker.smarthome.business.chart.configuration;


import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;

public interface ChartConfig {
    String getJsonChartConfig() throws IOException;
    String getJsonRequestDataConfig() throws JsonProcessingException;
    double getRefreshInterval();
}
