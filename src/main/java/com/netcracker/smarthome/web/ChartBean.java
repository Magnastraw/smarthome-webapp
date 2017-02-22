package com.netcracker.smarthome.web;

import com.netcracker.smarthome.business.chart.ChartService;
import com.netcracker.smarthome.model.entities.MetricHistory;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@ManagedBean(name = "chartBean")
@SessionScoped
public class ChartBean {
    @ManagedProperty(value = "#{ChartService}")
    ChartService chartService;
    private String chartConfig;
    private String dataSourceConfig;
    private double refreshInterval;
    private MetricHistory metricHistory;

    public ChartBean() {
        metricHistory = new MetricHistory();
    }

    public ChartService getChartService() {
        return chartService;
    }

    public void setChartService(ChartService chartService) {
        this.chartService = chartService;
    }

    public String getChartConfig() {
        JSONObject jsonConfig = new JSONObject();
        jsonConfig.put("credits", new JSONObject().put("enabled", false));
        jsonConfig.put("chart", new JSONObject().put("renderTo", "component_chartDiv")
                .put("zoomType", "x")
                .put("type", "line"));
        jsonConfig.put("title", new JSONObject().put("text", "Magic title"));
        jsonConfig.put("xAxis", new JSONObject().put("title", new JSONObject())
                                                .put("type", "datetime"));
        jsonConfig.put("lang", new JSONObject().put("fullscreenTooltip", "Fullscreen"));
        jsonConfig.put("exporting", new JSONObject().put("buttons", new JSONObject()
                .put("customButton", new JSONObject()
                        .put("_titleKey", "fullscreenTooltip")
                        .put("x", -40)
                        .put("symbol", "symbolFullscreen"))));

        chartConfig = jsonConfig.toString();

        return chartConfig;
    }

    public void setChartConfig() {

    }

    public String getDataSourceConfig() {
        JSONObject jsonConfig = new JSONObject();
        jsonConfig.put("homeId", "1");
        jsonConfig.put("type", "m");
        jsonConfig.put("objectId", "2");
        jsonConfig.put("subObjectId", "1");
        jsonConfig.put("metricSpecId", "2");
        dataSourceConfig = jsonConfig.toString();
        return dataSourceConfig;
    }

    public void setDataSourceConfig() {

    }

    public String getDataSourceConfigTwo() {
        JSONObject jsonConfig = new JSONObject();
        jsonConfig.put("homeId", "1");
        jsonConfig.put("type", "o");
        jsonConfig.put("objectId", "1");
        jsonConfig.put("subObjectId", "1");
        jsonConfig.put("metricSpecId", "2");
        dataSourceConfig = jsonConfig.toString();
        return dataSourceConfig;
    }

    public void setDataSourceConfigTwo() {

    }

    public double getRefreshInterval() {
        return 0;
    }

    public void setRefreshInterval(double refreshInterval) {
        this.refreshInterval = refreshInterval;
    }


}
