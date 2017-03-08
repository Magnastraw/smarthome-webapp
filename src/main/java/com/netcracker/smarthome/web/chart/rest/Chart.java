package com.netcracker.smarthome.web.chart.rest;

import com.netcracker.smarthome.web.chart.options.jsonfields.DataSeries;
import com.netcracker.smarthome.web.chart.options.RequestDataOptions;
import java.util.ArrayList;

interface Chart {
    ArrayList<DataSeries> configure(RequestDataOptions requestDataOptions);
}


