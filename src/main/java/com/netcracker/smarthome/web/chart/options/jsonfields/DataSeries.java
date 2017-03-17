package com.netcracker.smarthome.web.chart.options.jsonfields;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


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

    public void addLiveData(Object[] metricHistory) {
        Timestamp readDate = (Timestamp) metricHistory[1];
        BigDecimal value = (BigDecimal) metricHistory[2];
        Data data = new Data();
        data.setX(readDate.getTime());
        data.setY(value);
        this.data.add(data);
    }

    public void addDayData(Object[] metricHistory) throws ParseException {
        String readDate = (String) metricHistory[0];
        BigDecimal value = (BigDecimal) metricHistory[1];
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedDate = dateFormat.parse(readDate);
        Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
        Data data = new Data();
        data.setX(timestamp.getTime());
        data.setY(value);
        this.data.add(data);
    }

}
