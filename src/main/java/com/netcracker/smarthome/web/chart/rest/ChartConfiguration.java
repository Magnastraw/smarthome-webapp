package com.netcracker.smarthome.web.chart.rest;


import com.netcracker.smarthome.business.chart.options.RequestDataOptions;
import com.netcracker.smarthome.business.chart.options.jsonfields.DataSeries;
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

    public ArrayList<DataSeries> getData(RequestDataOptions requestDataOptions) throws ParseException {
        return chart.configure(requestDataOptions);
    }
}
