package com.netcracker.smarthome.business.chart.configuration;

import com.netcracker.smarthome.business.services.ChartService;
import com.netcracker.smarthome.model.entities.MetricSpec;
import com.netcracker.smarthome.model.entities.SmartHome;
import com.netcracker.smarthome.model.entities.SmartObject;
import com.netcracker.smarthome.business.chart.options.jsonfields.SeriesConfig;
import com.netcracker.smarthome.business.chart.options.jsonfields.YAxisNumber;

import java.util.ArrayList;
import java.util.List;

public class MultiChartConfig extends DefaultChartConfig {
    private String title;

    public MultiChartConfig(SmartHome smartHome, long chartId, String chartTitle, ChartService chartService, long refreshInterval, String chartType, String chartInterval) {
        super(smartHome, chartId, refreshInterval, chartType, chartInterval,chartService);
        this.title = chartTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public HighchartConfig configure(List<MetricSpec> selectedMetricSpecs, List<SmartObject> selectedSmartObjects) {
        super.getHighchartConfig().getChartOptions().setChartTitle(getTitle());
        ArrayList<YAxisNumber> yAxisNumbers = super.setAxisOptions(selectedMetricSpecs, selectedSmartObjects, chartService);

        for (SmartObject smartObject : selectedSmartObjects) {
            for (MetricSpec metricSpec : chartService.getMetricsSpecByObjectId(smartObject.getSmartObjectId())) {
                SeriesConfig seriesConfig = new SeriesConfig();
                seriesConfig.setData("");
                seriesConfig.setName(smartObject.getName());
                seriesConfig.setType(super.getChartType());
                for (YAxisNumber yAxisNumber : yAxisNumbers) {
                    if (yAxisNumber.getSpecId() == metricSpec.getSpecId()) {
                        seriesConfig.setyAxis(yAxisNumber.getNumber());
                    }
                }
                super.getHighchartConfig().getChartOptions().getSeries().add(seriesConfig);
            }
            super.getHighchartConfig().getRequestDataOptions().getObjectId().add(smartObject.getSmartObjectId());
        }

        return super.getHighchartConfig();
    }

    private boolean isChild(SmartObject smartObject) {
        return smartObject.getParentObject() != null;
    }
}
