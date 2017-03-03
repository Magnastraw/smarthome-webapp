package com.netcracker.smarthome.web.chart.rest;

import com.netcracker.smarthome.web.chart.highchartConfigurations.DataSeries;
import com.netcracker.smarthome.web.chart.highchartConfigurations.RequestDataOptions;
import java.util.ArrayList;

interface Chart {
    ArrayList<DataSeries> configure(RequestDataOptions requestDataOptions);
}


