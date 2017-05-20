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


public abstract class DefaultChartConfig implements ChartConfigurator {

    private int yAxisNumber;
    private HighchartConfig highchartConfig;
    private String chartType;
    protected ChartService chartService;

    public DefaultChartConfig(SmartHome smartHome, long chartId, long refreshInterval, String chartType, String chartInterval,ChartService chartService) {
        this.chartService = chartService;
        highchartConfig = new HighchartConfig(new ChartOptions(new ArrayList<AxisConfig>(), new ArrayList<SeriesConfig>()), new RequestDataOptions(new ArrayList<Long>(), new ArrayList<Long>()));
        highchartConfig.setChartId(chartId);
        highchartConfig.setRefreshInterval(refreshInterval);

        highchartConfig.getRequestDataOptions().setSmartHomeId(smartHome.getSmartHomeId());
        highchartConfig.getRequestDataOptions().setChartInterval(chartInterval);
        setChartType(chartType);

        highchartConfig.getChartOptions().setxAxisType("datetime");
        highchartConfig.getChartOptions().setCredits("false");
        highchartConfig.getChartOptions().setRenderTo("component" + chartId + "_chartDiv");
        highchartConfig.getChartOptions().setType(chartType);
        highchartConfig.getChartOptions().setZoomType("x");
    }

    public String getChartType() {
        return chartType;
    }

    public void setChartType(String chartType) {
        this.chartType = chartType;
    }

    public HighchartConfig getHighchartConfig() {
        return highchartConfig;
    }

    public void setHighchartConfig(HighchartConfig chartConfigImpl) {
        this.highchartConfig = chartConfigImpl;
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
                axisConfig.setLabel(metricSpec.getUnit().getLabel());

                highchartConfig.getChartOptions().getyAxis().add(axisConfig);

                highchartConfig.getRequestDataOptions().getMetricSpecId().add(metricSpec.getSpecId());
            }
        }
        return yAxisNumbers;
    }

    @Override
    public abstract HighchartConfig configure(List<MetricSpec> selectedMetricSpecs, List<SmartObject> selectedSmartObjects);
}
