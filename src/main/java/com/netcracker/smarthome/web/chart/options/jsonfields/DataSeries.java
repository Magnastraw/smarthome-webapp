package com.netcracker.smarthome.web.chart.options.jsonfields;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DataSeries implements Series {

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
        data.setX(readDate.getTime());
        data.setY(value);
        this.data.add(data);
    }
}
