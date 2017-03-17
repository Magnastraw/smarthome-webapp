package com.netcracker.smarthome.web.chart.options;

import com.netcracker.smarthome.web.chart.options.jsonfields.AxisConfig;
import com.netcracker.smarthome.web.chart.options.jsonfields.SeriesConfig;

import java.util.ArrayList;
import java.util.HashMap;

public class ChartOptions {
    private String credits;
    private String chartTitle;
    private String zoomType;
    private String type;
    private String renderTo;
    private String xAxisType;
    private ArrayList<AxisConfig> yAxis;
    private ArrayList<SeriesConfig> series;

    public ChartOptions(ArrayList<AxisConfig> yAxis, ArrayList<SeriesConfig> series) {
        this.yAxis = yAxis;
        this.series = series;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public String getChartTitle() {
        return chartTitle;
    }

    public void setChartTitle(String chartTitle) {
        this.chartTitle = chartTitle;
    }

    public String getZoomType() {
        return zoomType;
    }

    public void setZoomType(String zoomType) {
        this.zoomType = zoomType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRenderTo() {
        return renderTo;
    }

    public void setRenderTo(String renderTo) {
        this.renderTo = renderTo;
    }

    public String getxAxisType() {
        return xAxisType;
    }

    public void setxAxisType(String xAxisType) {
        this.xAxisType = xAxisType;
    }

    public ArrayList<AxisConfig> getyAxis() {
        return yAxis;
    }

    public void setyAxis(ArrayList<AxisConfig> yAxis) {
        this.yAxis = yAxis;
    }

    public ArrayList<SeriesConfig> getSeries() {
        return series;
    }

    public void setSeries(ArrayList<SeriesConfig> series) {
        this.series = series;
    }

}
