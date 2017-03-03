package com.netcracker.smarthome.web.chart.rest;

import com.netcracker.smarthome.business.chart.ChartService;
import com.netcracker.smarthome.model.entities.*;
import com.netcracker.smarthome.web.chart.highchartConfigurations.DataSeries;
import com.netcracker.smarthome.web.chart.highchartConfigurations.RequestDataOptions;
import com.netcracker.smarthome.web.common.ContextUtils;
import org.primefaces.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
public class ChartController {

    @Autowired
    ChartService chartService;

    @RequestMapping(value={"/jsonData"}, method = RequestMethod.POST)
    @ResponseBody
    public ArrayList<DataSeries> getJsonData(@RequestBody RequestDataOptions requestDataOptions) {
        ChartConfiguration chartConfiguration = new ChartConfiguration();

        switch (requestDataOptions.getType()) {
            case 'm':
                chartConfiguration.setChart(new MetricSpecToObjectChart(chartService));
                return chartConfiguration.getData(requestDataOptions);
            case 'o':
                chartConfiguration.setChart(new ObjectToMetricSpecChart(chartService));
                return chartConfiguration.getData(requestDataOptions);
            default:
                return null;
        }
    }

    @RequestMapping(value = {"/jsonDataLive"}, method = RequestMethod.POST)
    @ResponseBody
    public String getJsonDataLive(@RequestParam("type") char type, @RequestParam("homeId") long homeId, @RequestParam("objectId") long objectId, @RequestParam("subObjectId") long subObjectId, @RequestParam("metricSpecId") long metricSpecId, @RequestParam("rownum") int rownum) {
        User user = ContextUtils.getCurrentUser();
        JSONArray resultArray = new JSONArray();
        return resultArray.toString();
    }

}
