package com.netcracker.smarthome.web.chart.options;

import java.util.ArrayList;

public class RequestDataOptions {
    private long smartHomeId;
    private char type;
    private ArrayList<Long> objectId;
    private ArrayList<Long> metricSpecId;
    private int rownum;
    private int seriesSize;

    public RequestDataOptions() {}

    public RequestDataOptions(ArrayList<Long> objectId, ArrayList<Long> metricSpecId) {
        this.objectId = objectId;
        this.metricSpecId = metricSpecId;
    }

    public Long getSmartHomeId() {
        return smartHomeId;
    }

    public void setSmartHomeId(long smartHomeId) {
        this.smartHomeId = smartHomeId;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }

    public ArrayList<Long> getObjectId() {
        return objectId;
    }

    public void setObjectId(ArrayList<Long> objectId) {
        this.objectId = objectId;
    }

    public ArrayList<Long> getMetricSpecId() {
        return metricSpecId;
    }

    public void setMetricSpecId(ArrayList<Long> metricSpecId) {
        this.metricSpecId = metricSpecId;
    }

    public int getRownum() {
        return rownum;
    }

    public void setRownum(int rownum) {
        this.rownum = rownum;
    }

    public int getSeriesSize() {
        return seriesSize;
    }

    public void setSeriesSize(int seriesSize) {
        this.seriesSize = seriesSize;
    }
}
