package com.netcracker.smarthome.business.chart.options;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.netcracker.smarthome.model.enums.ChartInterval;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

public class RequestDataOptions {
    private long smartHomeId;
    private String chartInterval;
    private ArrayList<Long> objectId;
    private ArrayList<Long> metricSpecId;

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

    @JsonIgnore
    public Timestamp getTime(){
        Timestamp selectTime = new Timestamp(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(selectTime);
        calendar.add(ChartInterval.getEnum(chartInterval).getCalendarInterval(), ChartInterval.getEnum(chartInterval).getIntValue());
        return new Timestamp(calendar.getTime().getTime());
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
