package com.netcracker.smarthome.web.chart.highchartConfigurations;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DataSeries {
    private ArrayList<Data> data;

    public DataSeries(ArrayList<Data> data) {
        this.data = data;
    }

    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }

    public void addData(Object[] metricHistory){
        Timestamp readDate = (Timestamp) metricHistory[1];
        BigDecimal value = (BigDecimal) metricHistory[2];
        Data data = new Data();
        data.setX(new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(readDate));
        data.setY(value);
        this.data.add(data);
    }
}
