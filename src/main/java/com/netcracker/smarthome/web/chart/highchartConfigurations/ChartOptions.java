package com.netcracker.smarthome.web.chart.highchartConfigurations;

import java.util.ArrayList;
import java.util.HashMap;

public class ChartOptions {
    private HashMap<String, Boolean> credits;
    private HashMap<String, String> chart;
    private HashMap<String, String> title;
    private HashMap<String, String> lang;
    private HashMap<String, String> xAxis;
    private ArrayList<AxisConfig> yAxis;
    private HashMap<String, HashMap<String, HashMap<String, String>>> exporting;
    private ArrayList<SeriesConfig> series;

    public HashMap<String, Boolean> getCredits() {
        return credits;
    }

    public void setCredits(HashMap<String, Boolean> credits) {
        this.credits = credits;
    }

    public HashMap<String, String> getChart() {
        return chart;
    }

    public void setChart(HashMap<String, String> chart) {
        this.chart = chart;
    }

    public HashMap<String, String> getTitle() {
        return title;
    }

    public void setTitle(HashMap<String, String> title) {
        this.title = title;
    }

    public HashMap<String, String> getLang() {
        return lang;
    }

    public void setLang(HashMap<String, String> lang) {
        this.lang = lang;
    }

    public HashMap<String, String> getxAxis() {
        return xAxis;
    }

    public void setxAxis(HashMap<String, String> xAxis) {
        this.xAxis = xAxis;
    }

    public ArrayList<AxisConfig> getyAxis() {
        return yAxis;
    }

    public void setyAxis(ArrayList<AxisConfig> yAxis) {
        this.yAxis = yAxis;
    }

    public HashMap<String, HashMap<String, HashMap<String, String>>> getExporting() {
        return exporting;
    }

    public void setExporting(HashMap<String, HashMap<String, HashMap<String, String>>> exporting) {
        this.exporting = exporting;
    }

    public ArrayList<SeriesConfig> getSeries() {
        return series;
    }

    public void setSeries(ArrayList<SeriesConfig> series) {
        this.series = series;
    }
}
