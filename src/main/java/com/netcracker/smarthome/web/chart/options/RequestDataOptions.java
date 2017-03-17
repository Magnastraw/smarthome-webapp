package com.netcracker.smarthome.web.chart.options;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;

public class RequestDataOptions {
    private long smartHomeId;
    private String chartInterval;
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

    public String getChartInterval() {
        return chartInterval;
    }

    public void setChartInterval(String chartInterval) {
        this.chartInterval = chartInterval;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("smartHomeId", getSmartHomeId())
                .append("chartInterval", getChartInterval())
                .append("objectId", getObjectId().toString())
                .append("metricSpecId", getMetricSpecId().toString())
                .toString();
    }
}
