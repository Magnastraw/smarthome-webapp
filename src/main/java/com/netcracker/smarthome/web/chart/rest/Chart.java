package com.netcracker.smarthome.web.chart.rest;

import com.netcracker.smarthome.web.chart.options.RequestDataOptions;
import com.netcracker.smarthome.web.chart.options.jsonfields.Series;

import java.util.ArrayList;

interface Chart {
    ArrayList<Series> configure(RequestDataOptions requestDataOptions);
}


