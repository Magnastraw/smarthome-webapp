package com.netcracker.smarthome.business.chart.configuration;

import com.netcracker.smarthome.business.services.ChartService;
import com.netcracker.smarthome.model.entities.MetricSpec;
import com.netcracker.smarthome.model.entities.SmartHome;
import com.netcracker.smarthome.model.entities.SmartObject;
import com.netcracker.smarthome.business.chart.options.jsonfields.AxisConfig;
import com.netcracker.smarthome.business.chart.options.jsonfields.SeriesConfig;


import java.util.List;

public class MetricChartConfig extends DefaultChartConfig {

    public MetricChartConfig(SmartHome smartHome, long chartId, ChartService chartService, long refreshInterval, String chartType, String chartInterval) {
        super(smartHome, chartId, refreshInterval, chartType, chartInterval, chartService);
    }

    public HighchartConfig configure(List<MetricSpec> selectedMetricSpecs, List<SmartObject> selectedSmartObjects) {

        super.getHighchartConfig().getChartOptions().setChartTitle(selectedMetricSpecs.get(0).getSpecName());
        AxisConfig axisConfig = new AxisConfig();
        axisConfig.setTitle(selectedMetricSpecs.get(0).getSpecName());
        axisConfig.setLabel(selectedMetricSpecs.get(0).getUnit().getLabel());
        super.getHighchartConfig().getChartOptions().getyAxis().add(axisConfig);

        for (SmartObject smartObject : selectedSmartObjects) {
            SeriesConfig seriesConfig = new SeriesConfig();
            seriesConfig.setData("");
            seriesConfig.setName(smartObject.getName());
            seriesConfig.setType(super.getChartType());
            super.getHighchartConfig().getChartOptions().getSeries().add(seriesConfig);
            super.getHighchartConfig().getRequestDataOptions().getObjectId().add(smartObject.getSmartObjectId());
        }
        super.getHighchartConfig().getRequestDataOptions().getMetricSpecId().add(selectedMetricSpecs.get(0).getSpecId());

        return super.getHighchartConfig();
    }
}
