package com.netcracker.smarthome.web.chart.rest;

import com.netcracker.smarthome.business.chart.options.RequestDataOptions;
import com.netcracker.smarthome.business.chart.options.jsonfields.DataSeries;

import java.text.ParseException;
import java.util.ArrayList;

interface Chart {
    ArrayList<DataSeries> configure(RequestDataOptions requestDataOptions) throws ParseException;
}


