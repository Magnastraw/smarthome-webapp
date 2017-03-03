package com.netcracker.smarthome.web.chart.highchartConfigurations;

import java.util.ArrayList;

public class RequestDataOptions {
    private long smartHomeId;
    private char type;
    private ArrayList<Long> objectId;
    private ArrayList<Long> metricSpecId;
    private ArrayList<Long> subObjectId;

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

    public ArrayList<Long> getSubObjectId() {
        return subObjectId;
    }

    public void setSubObjectId(ArrayList<Long> subObjectId) {
        this.subObjectId = subObjectId;
    }


}
