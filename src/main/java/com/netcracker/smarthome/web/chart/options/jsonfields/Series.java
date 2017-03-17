package com.netcracker.smarthome.web.chart.options.jsonfields;


import java.text.ParseException;

public interface Series {
    void addDayData(Object[] metricHistory) throws ParseException;
    void addLiveData(Object[] metricHistory);
}
