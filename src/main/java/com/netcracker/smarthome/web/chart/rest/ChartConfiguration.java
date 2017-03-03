package com.netcracker.smarthome.web.chart.rest;


import com.netcracker.smarthome.web.chart.highchartConfigurations.DataSeries;
import com.netcracker.smarthome.web.chart.highchartConfigurations.RequestDataOptions;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class ChartConfiguration {
    private Chart chart;

    public Chart getChart() {
        return chart;
    }

    public void setChart(Chart chart) {
        this.chart = chart;
    }

    public ArrayList<DataSeries> getData(RequestDataOptions requestDataOptions){
        return chart.configure(requestDataOptions);
    }
}
