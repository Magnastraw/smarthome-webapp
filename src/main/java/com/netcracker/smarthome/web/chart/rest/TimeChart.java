package com.netcracker.smarthome.web.chart.rest;

import com.netcracker.smarthome.business.chart.ChartService;
import com.netcracker.smarthome.model.entities.MetricHistory;
import com.netcracker.smarthome.model.entities.MetricSpec;
import com.netcracker.smarthome.model.entities.SmartObject;
import com.netcracker.smarthome.model.enums.ChartInterval;
import com.netcracker.smarthome.business.chart.options.jsonfields.Data;
import com.netcracker.smarthome.business.chart.options.jsonfields.DataSeries;
import com.netcracker.smarthome.business.chart.options.RequestDataOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.ArrayList;

@Component
public class TimeChart implements Chart {
    private static final Logger LOG = LoggerFactory.getLogger(TimeChart.class);

    private ArrayList<DataSeries> dataSerieses;

    private final ChartService chartService;

    @Autowired
    public TimeChart(ChartService chartService) {
        this.chartService = chartService;
    }

    public ChartService getChartService() {
        return chartService;
    }

    public ArrayList<DataSeries> getDataSeries() {
        return dataSerieses;
    }

    public void setDataSeries(ArrayList<DataSeries> dataSerieses) {
        this.dataSerieses = dataSerieses;
    }

    public ArrayList<DataSeries> configure(RequestDataOptions requestDataOptions) throws ParseException {
        dataSerieses = new ArrayList<DataSeries>();
        for (SmartObject smartObject : chartService.getSmartObjectByIds(requestDataOptions.getObjectId())) {
            for (MetricSpec metricSpec : chartService.getMetricsSpecByObjectId(smartObject.getSmartObjectId())) {
                if (isSelectedMetric(requestDataOptions.getMetricSpecId(), metricSpec.getSpecId())) {
                    DataSeries dataSeries = new DataSeries(new ArrayList<Data>());
                    if(requestDataOptions.getChartInterval().equals(ChartInterval.Live.toString().toLowerCase())){
                        for (MetricHistory metricHistory : chartService.getLastMetricHistory(requestDataOptions.getSmartHomeId(), metricSpec.getSpecId(), smartObject.getSmartObjectId())) {
                            dataSeries.addData(metricHistory);
                        }
                    } else {
                        for (MetricHistory metricHistory : chartService.getMetricsHistory(requestDataOptions.getSmartHomeId(), metricSpec.getSpecId(), smartObject.getSmartObjectId(), requestDataOptions.getTime())) {
                            dataSeries.addData(metricHistory);
                        }
                    }
                    dataSerieses.add(dataSeries);
                }
            }
        }
        return dataSerieses;
    }

    private boolean isSelectedMetric(ArrayList<Long> metricSpecIds, long metricSpecId) {
        for (Long id : metricSpecIds) {
            if (metricSpecId == id) {
                return true;
            }
        }
        return false;
    }
}
