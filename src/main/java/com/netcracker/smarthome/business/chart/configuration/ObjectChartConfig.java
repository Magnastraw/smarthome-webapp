package com.netcracker.smarthome.business.chart.configuration;

import com.netcracker.smarthome.business.services.ChartService;
import com.netcracker.smarthome.model.entities.MetricSpec;
import com.netcracker.smarthome.model.entities.SmartHome;
import com.netcracker.smarthome.model.entities.SmartObject;
import com.netcracker.smarthome.business.chart.options.jsonfields.SeriesConfig;
import com.netcracker.smarthome.business.chart.options.jsonfields.YAxisNumber;

import java.util.ArrayList;
import java.util.List;

public class ObjectChartConfig extends DefaultChartConfig {

    public ObjectChartConfig(SmartHome smartHome, long chartId, ChartService chartService, long refreshInterval, String chartType, String chartInterval) {
        super(smartHome, chartId, refreshInterval, chartType, chartInterval,chartService);
    }

    public HighchartConfig configure(List<MetricSpec> selectedMetricSpecs, List<SmartObject> selectedSmartObjects) {

        super.getHighchartConfig().getChartOptions().setChartTitle(selectedSmartObjects.get(0).getName());
        ArrayList<YAxisNumber> yAxisNumbers = super.setAxisOptions(selectedMetricSpecs, selectedSmartObjects, chartService);

        for (SmartObject smartObject : selectedSmartObjects) {
            for (MetricSpec metricSpec : chartService.getMetricsSpecByObjectId(smartObject.getSmartObjectId())) {
                SeriesConfig seriesConfig = new SeriesConfig();
                seriesConfig.setData("");
                seriesConfig.setName(metricSpec.getSpecName());
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

}
