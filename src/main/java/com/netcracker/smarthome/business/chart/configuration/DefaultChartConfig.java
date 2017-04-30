package com.netcracker.smarthome.business.chart.configuration;


import com.netcracker.smarthome.business.services.ChartService;
import com.netcracker.smarthome.model.entities.MetricSpec;
import com.netcracker.smarthome.model.entities.SmartHome;
import com.netcracker.smarthome.business.chart.options.ChartOptions;
import com.netcracker.smarthome.business.chart.options.RequestDataOptions;
import com.netcracker.smarthome.business.chart.options.jsonfields.AxisConfig;
import com.netcracker.smarthome.business.chart.options.jsonfields.SeriesConfig;
import com.netcracker.smarthome.business.chart.options.jsonfields.YAxisNumber;
import com.netcracker.smarthome.model.entities.SmartObject;

import java.util.ArrayList;
import java.util.List;


public class DefaultChartConfig {

    private int yAxisNumber;
    private ChartConfigImpl chartConfigImpl;
    private String chartType;
    private final double TO_MILLISECONDS_CONVERT = 60000;

    public DefaultChartConfig(SmartHome smartHome, long chartId, double refreshInterval, String chartType, String chartInterval) {

        chartConfigImpl = new ChartConfigImpl(new ChartOptions(new ArrayList<AxisConfig>(), new ArrayList<SeriesConfig>()), new RequestDataOptions(new ArrayList<Long>(), new ArrayList<Long>()));
        chartConfigImpl.setChartId(chartId);
        chartConfigImpl.setRefreshInterval(refreshInterval * TO_MILLISECONDS_CONVERT);

        chartConfigImpl.getRequestDataOptions().setSmartHomeId(smartHome.getSmartHomeId());
        chartConfigImpl.getRequestDataOptions().setChartInterval(chartInterval);
        setChartType(chartType);

        chartConfigImpl.getChartOptions().setxAxisType("datetime");
        chartConfigImpl.getChartOptions().setCredits("false");
        chartConfigImpl.getChartOptions().setRenderTo("component" + chartId + "_chartDiv");
        chartConfigImpl.getChartOptions().setType(chartType);
        chartConfigImpl.getChartOptions().setZoomType("x");
    }

    public String getChartType() {
        return chartType;
    }

    public void setChartType(String chartType) {
        this.chartType = chartType;
    }

    public ChartConfigImpl getChartConfigImpl() {
        return chartConfigImpl;
    }

    public void setChartConfigImpl(ChartConfigImpl chartConfigImpl) {
        this.chartConfigImpl = chartConfigImpl;
    }

    public ArrayList<YAxisNumber> setAxisOptions(List<MetricSpec> selectedMetricSpecs, List<SmartObject> selectedSmartObject, ChartService chartService) {
        ArrayList<YAxisNumber> yAxisNumbers = new ArrayList<YAxisNumber>();
        List<MetricSpec> supportedMetricSpecs = chartService.getSupportedMetricSpecs(selectedSmartObject);
        for (MetricSpec metricSpec : selectedMetricSpecs) {
            if (supportedMetricSpecs.contains(metricSpec)) {

                AxisConfig axisConfig = new AxisConfig();
                YAxisNumber yAxisNumber = new YAxisNumber();

                yAxisNumber.setNumber(this.yAxisNumber++);
                yAxisNumber.setSpecId(metricSpec.getSpecId());
                yAxisNumbers.add(yAxisNumber);

                axisConfig.setTitle(metricSpec.getSpecName());
                axisConfig.setLabel(chartService.getUnitBySpecId(metricSpec.getSpecId()).getLabel());

                chartConfigImpl.getChartOptions().getyAxis().add(axisConfig);

                chartConfigImpl.getRequestDataOptions().getMetricSpecId().add(metricSpec.getSpecId());
            }
        }
        return yAxisNumbers;
    }

}
