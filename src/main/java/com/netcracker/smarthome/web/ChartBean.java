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
                .put("type", "spline"));
        jsonConfig.put("title", new JSONObject().put("text", "Magic title"));
        jsonConfig.put("subtitle", new JSONObject().put("text", "Sub title"));
        jsonConfig.put("xAxis", new JSONObject().put("title", new JSONObject()
                .put("text", "x")).put("type", "datetime"));
        jsonConfig.put("yAxis", new JSONObject().put("title", new JSONObject()
                .put("text", "y")));
        jsonConfig.put("lang", new JSONObject().put("fullscreenTooltip", "Fullscreen"));
        jsonConfig.put("exporting", new JSONObject().put("buttons", new JSONObject()
                .put("customButton", new JSONObject()
                        .put("_titleKey", "fullscreenTooltip")
                        .put("x", -40)
                        .put("symbol", "symbolFullscreen"))));
        jsonConfig.put("series", new JSONArray().put(new JSONObject()).put(new JSONObject()));

        chartConfig = jsonConfig.toString();

        return chartConfig;
    }

    public void setChartConfig() {

    }

    public String getDataSourceConfig() {
        JSONObject jsonConfig = new JSONObject();
        jsonConfig.put("homeId","1");
        jsonConfig.put("objectId", "1");
        jsonConfig.put("subObjectId", "1");
        jsonConfig.put("metricSpecId","1");
        dataSourceConfig = jsonConfig.toString();
        return dataSourceConfig;
    }

    public void setDataSourceConfig() {

    }

    public double getRefreshInterval() {
        return 0;
    }

    public void setRefreshInterval(double refreshInterval) {
        this.refreshInterval = refreshInterval;
    }

//    /*test*/
//    public void addMetricHistory() throws ParseException {
//        metricHistory.setHistoryId(0);
//        metricHistory.setReadDate(new Timestamp(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse("2017/02/14 21:45:11").getTime()));
//        metricHistory.setValue(new BigInteger("28"));
//        metricHistory.setMetric(chartService.getMetricById(11));
//        chartService.addMetricHistory(metricHistory);
//    }


}
