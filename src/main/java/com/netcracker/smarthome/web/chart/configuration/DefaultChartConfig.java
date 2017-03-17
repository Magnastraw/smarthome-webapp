package com.netcracker.smarthome.web.chart.configuration;


import com.netcracker.smarthome.business.chart.ChartService;
import com.netcracker.smarthome.model.entities.MetricSpec;
import com.netcracker.smarthome.model.entities.SmartHome;
import com.netcracker.smarthome.model.entities.SmartObject;
import com.netcracker.smarthome.web.chart.options.ChartOptions;
import com.netcracker.smarthome.web.chart.options.RequestDataOptions;
import com.netcracker.smarthome.web.chart.options.jsonfields.AxisConfig;
import com.netcracker.smarthome.web.chart.options.jsonfields.SeriesConfig;
import com.netcracker.smarthome.web.chart.options.jsonfields.YAxisNumber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DefaultChartConfig {

    private ChartConfig chartConfig;
    private int yAxisNumber;
    private String chartType;
    private final double TO_MILLISECONDS_CONVERT = 60000;

    public DefaultChartConfig(SmartHome smartHome, long chartId, double refreshInterval, String chartType, String chartInterval) {

        chartConfig = new ChartConfig(new ChartOptions(new ArrayList<AxisConfig>(), new ArrayList<SeriesConfig>()), new RequestDataOptions(new ArrayList<Long>(), new ArrayList<Long>()));
        chartConfig.setChartId(chartId);
        chartConfig.setRefreshInterval(refreshInterval * TO_MILLISECONDS_CONVERT);

        chartConfig.getRequestDataOptions().setSmartHomeId(smartHome.getSmartHomeId());
        chartConfig.getRequestDataOptions().setChartInterval(chartInterval);
        setChartType(chartType);

        chartConfig.getChartOptions().setxAxisType("datetime");
        chartConfig.getChartOptions().setCredits("false");
        chartConfig.getChartOptions().setRenderTo("component" + chartId + "_chartDiv");
        chartConfig.getChartOptions().setType(chartType);
        chartConfig.getChartOptions().setZoomType("x");
    }

    public String getChartType() {
        return chartType;
    }

    public void setChartType(String chartType) {
        this.chartType = chartType;
    }

    public ChartConfig getChartConfig() {
        return chartConfig;
    }

    public void setChartConfig(ChartConfig chartConfig) {
        this.chartConfig = chartConfig;
    }

    public int getyAxisNumber() {
        return yAxisNumber;
    }

    public void setyAxisNumber(int yAxisNumber) {
        this.yAxisNumber = yAxisNumber;
    }

    public void setInterval() {
        if (chartConfig.getRefreshInterval() == 0 || chartConfig.getRefreshInterval() > 5000) {
            chartConfig.getRequestDataOptions().setSeriesSize(100);
        } else {
            chartConfig.getRequestDataOptions().setSeriesSize(1);
        }
    }

    public List<SmartObject> deleteParentObj(List<SmartObject> selectedSubObject, List<SmartObject> selectedSmartObjects) {
        for (SmartObject smartObject : selectedSubObject) {
            for (int i = 0; i < selectedSmartObjects.size(); i++) {
                if (smartObject.getParentObject().getSmartObjectId() == selectedSmartObjects.get(i).getSmartObjectId()) {
                    selectedSmartObjects.remove(i);
                }
            }
            selectedSmartObjects.add(smartObject);
        }
        return selectedSmartObjects;
    }

    public ArrayList<YAxisNumber> setAxisOptions(List<MetricSpec> selectedMetricSpecs, ChartService chartService) {
        ArrayList<YAxisNumber> yAxisNumbers = new ArrayList<YAxisNumber>();
        for (MetricSpec metricSpec : selectedMetricSpecs) {

            AxisConfig axisConfig = new AxisConfig();
            YAxisNumber yAxisNumber = new YAxisNumber();

            yAxisNumber.setNumber(this.yAxisNumber++);
            yAxisNumber.setSpecId(metricSpec.getSpecId());
            yAxisNumbers.add(yAxisNumber);

            axisConfig.setTitle(metricSpec.getSpecName());
            axisConfig.setLabel(chartService.getUnitBySpecId(metricSpec.getSpecId()).getLabel());

            chartConfig.getChartOptions().getyAxis().add(axisConfig);

            chartConfig.getRequestDataOptions().getMetricSpecId().add(metricSpec.getSpecId());
        }
        return yAxisNumbers;
    }
}
