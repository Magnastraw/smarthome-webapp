package com.netcracker.smarthome.business.chart.configuration;


import com.netcracker.smarthome.business.chart.ChartService;
import com.netcracker.smarthome.model.entities.MetricSpec;
import com.netcracker.smarthome.model.entities.SmartHome;
import com.netcracker.smarthome.business.chart.options.ChartOptions;
import com.netcracker.smarthome.business.chart.options.RequestDataOptions;
import com.netcracker.smarthome.business.chart.options.jsonfields.AxisConfig;
import com.netcracker.smarthome.business.chart.options.jsonfields.SeriesConfig;
import com.netcracker.smarthome.business.chart.options.jsonfields.YAxisNumber;

import java.util.ArrayList;
import java.util.List;


public class DefaultChartConfig {

    private int yAxisNumber;
    private ChartConfigIml chartConfigIml;
    private String chartType;
    private final double TO_MILLISECONDS_CONVERT = 60000;

    public DefaultChartConfig(SmartHome smartHome, long chartId, double refreshInterval, String chartType, String chartInterval) {

        chartConfigIml = new ChartConfigIml(new ChartOptions(new ArrayList<AxisConfig>(), new ArrayList<SeriesConfig>()), new RequestDataOptions(new ArrayList<Long>(), new ArrayList<Long>()));
        chartConfigIml.setChartId(chartId);
        chartConfigIml.setRefreshInterval(refreshInterval * TO_MILLISECONDS_CONVERT);

        chartConfigIml.getRequestDataOptions().setSmartHomeId(smartHome.getSmartHomeId());
        chartConfigIml.getRequestDataOptions().setChartInterval(chartInterval);
        setChartType(chartType);

        chartConfigIml.getChartOptions().setxAxisType("datetime");
        chartConfigIml.getChartOptions().setCredits("false");
        chartConfigIml.getChartOptions().setRenderTo("component" + chartId + "_chartDiv");
        chartConfigIml.getChartOptions().setType(chartType);
        chartConfigIml.getChartOptions().setZoomType("x");
    }

    public String getChartType() {
        return chartType;
    }

    public void setChartType(String chartType) {
        this.chartType = chartType;
    }

    public ChartConfigIml getChartConfigIml() {
        return chartConfigIml;
    }

    public void setChartConfigIml(ChartConfigIml chartConfigIml) {
        this.chartConfigIml = chartConfigIml;
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

            chartConfigIml.getChartOptions().getyAxis().add(axisConfig);

            chartConfigIml.getRequestDataOptions().getMetricSpecId().add(metricSpec.getSpecId());
        }
        return yAxisNumbers;
    }
}
