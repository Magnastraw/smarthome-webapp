package com.netcracker.smarthome.web.chart.rest;

import com.netcracker.smarthome.business.chart.ChartService;
import com.netcracker.smarthome.model.entities.MetricSpec;
import com.netcracker.smarthome.web.chart.highchartConfigurations.Data;
import com.netcracker.smarthome.web.chart.highchartConfigurations.DataSeries;
import com.netcracker.smarthome.web.chart.highchartConfigurations.RequestDataOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class ObjectToMetricSpecChart implements Chart {
    private ArrayList<DataSeries> dataSerieses;

    private ChartService chartService;

    @Autowired
    public ObjectToMetricSpecChart(ChartService chartService) {
        this.chartService = chartService;
    }

    public ChartService getChartService() {
        return chartService;
    }

    public void setChartService(ChartService chartService) {
        this.chartService = chartService;
    }

    public ArrayList<DataSeries> configure(RequestDataOptions requestDataOptions) {
        dataSerieses = new ArrayList<DataSeries>();
        for (MetricSpec metricSpec : chartService.getMetricSpecByIds(requestDataOptions.getMetricSpecId())) {
            DataSeries dataSeries = new DataSeries(new ArrayList<Data>());
            for (Object[] metricHistory : chartService.getMetricsHistoryBySpecIdObjectId(requestDataOptions.getSmartHomeId(), metricSpec.getSpecId(), requestDataOptions.getObjectId().get(0))) {
                dataSeries.addData(metricHistory);
            }
            dataSerieses.add(dataSeries);
        }
        return dataSerieses;
    }

    public ArrayList<DataSeries> getDataSerieses() {
        return dataSerieses;
    }

    public void setDataSerieses(ArrayList<DataSeries> dataSerieses) {
        this.dataSerieses = dataSerieses;
    }
}
