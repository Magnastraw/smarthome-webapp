package com.netcracker.smarthome.web.chart.rest;

import com.netcracker.smarthome.business.chart.ChartService;
import com.netcracker.smarthome.model.entities.MetricSpec;
import com.netcracker.smarthome.model.entities.SmartObject;
import com.netcracker.smarthome.web.chart.options.jsonfields.Data;
import com.netcracker.smarthome.web.chart.options.jsonfields.DataSeries;
import com.netcracker.smarthome.web.chart.options.RequestDataOptions;
import com.netcracker.smarthome.web.chart.options.jsonfields.Series;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class MultiChart implements Chart {
    private ArrayList<Series> dataSerieses;

    private final ChartService chartService;

    @Autowired
    public MultiChart(ChartService chartService) {
        this.chartService = chartService;
    }

    public ChartService getChartService() {
        return chartService;
    }

    public ArrayList<Series> getDataSeries() {
        return dataSerieses;
    }

    public void setDataSeries(ArrayList<Series> dataSerieses) {
        this.dataSerieses = dataSerieses;
    }

    public ArrayList<Series> configure(RequestDataOptions requestDataOptions) {
        dataSerieses = new ArrayList<Series>();
        for (SmartObject smartObject : chartService.getSmartObjectByIds(requestDataOptions.getObjectId())) {
            for (MetricSpec metricSpec : chartService.getMetricsSpecByObjectId(smartObject.getSmartObjectId())) {
                if(isSelectedMetric(requestDataOptions.getMetricSpecId(),metricSpec.getSpecId())){
                    DataSeries dataSeries = new DataSeries(new ArrayList<Data>());
                    for (Object[] metricHistory : chartService.getMetricsHistoryBySpecIdObjectId(requestDataOptions.getSmartHomeId(), metricSpec.getSpecId(), smartObject.getSmartObjectId(),requestDataOptions.getRownum(),requestDataOptions.getSeriesSize())) {
                        dataSeries.addData(metricHistory);
                    }
                    dataSerieses.add(dataSeries);
                }
            }
        }
        return dataSerieses;
    }

    private boolean isSelectedMetric(ArrayList<Long> metricSpecIds, long metricSpecId){
        for(Long id:metricSpecIds){
            if(metricSpecId==id){
                return true;
            }
        }
        return false;
    }
}
