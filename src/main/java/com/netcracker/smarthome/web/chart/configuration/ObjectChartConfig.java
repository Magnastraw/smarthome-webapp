package com.netcracker.smarthome.web.chart.configuration;

import com.netcracker.smarthome.business.chart.ChartService;
import com.netcracker.smarthome.model.entities.MetricSpec;
import com.netcracker.smarthome.model.entities.SmartHome;
import com.netcracker.smarthome.model.entities.SmartObject;
import com.netcracker.smarthome.web.chart.options.jsonfields.SeriesConfig;
import com.netcracker.smarthome.web.chart.options.jsonfields.YAxisNumber;

import java.util.ArrayList;
import java.util.List;

public class ObjectChartConfig extends DefaultChartConfig implements ChartConfigInterface {
    private ChartService chartService;

    public ObjectChartConfig(SmartHome smartHome, int chartId, ChartService chartService, double refreshInterval) {
        super(smartHome, chartId, refreshInterval);
        this.chartService = chartService;
    }

    public ChartConfig configure(List<MetricSpec> selectedMetricSpecs, List<SmartObject> selectedSmartObjects, List<SmartObject> selectedSubObject) {

        super.getChartConfig().getChartOptions().getTitle().put("text", selectedSmartObjects.get(0).getName());

        selectedSmartObjects = super.deleteParentObj(selectedSubObject, selectedSmartObjects);

        ArrayList<YAxisNumber> yAxisNumbers = super.setAxisOptions(selectedMetricSpecs, chartService);

        for (SmartObject smartObject : selectedSmartObjects) {
            for (MetricSpec metricSpec : chartService.getMetricsSpecByObjectId(smartObject.getSmartObjectId())) {
                SeriesConfig seriesConfig = new SeriesConfig();
                seriesConfig.setData("");
                seriesConfig.setName(metricSpec.getSpecName());//?
                seriesConfig.setType("line");
                for (YAxisNumber yAxisNumber : yAxisNumbers) {
                    if (yAxisNumber.getSpecId() == metricSpec.getSpecId()) {
                        seriesConfig.setyAxis(yAxisNumber.getNumber());
                    }
                }
                super.getChartConfig().getChartOptions().getSeries().add(seriesConfig);
            }
            super.getChartConfig().getRequestDataOptions().getObjectId().add(smartObject.getSmartObjectId());
        }
        super.getChartConfig().getRequestDataOptions().setType('d');
        super.setInterval();

        return super.getChartConfig();
    }

}
