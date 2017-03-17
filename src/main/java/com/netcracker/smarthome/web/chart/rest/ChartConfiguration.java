package com.netcracker.smarthome.web.chart.rest;


import com.netcracker.smarthome.web.chart.options.jsonfields.DataSeries;
import com.netcracker.smarthome.web.chart.options.RequestDataOptions;
import com.netcracker.smarthome.web.chart.options.jsonfields.Series;
import org.springframework.stereotype.Component;

import java.text.ParseException;
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

    public ArrayList<Series> getData(RequestDataOptions requestDataOptions) throws ParseException {
        return chart.configure(requestDataOptions);
    }
}
