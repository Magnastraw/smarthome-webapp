package com.netcracker.smarthome.web.chart.rest;

import com.netcracker.smarthome.business.chart.ChartService;
import com.netcracker.smarthome.web.chart.options.jsonfields.DataSeries;
import com.netcracker.smarthome.web.chart.options.RequestDataOptions;
import com.netcracker.smarthome.web.chart.options.jsonfields.Series;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

@Controller
public class ChartController {
    private ChartConfiguration chartConfiguration;

    @Autowired
    ChartService chartService;
    @PostConstruct
    private void init(){
        chartConfiguration = new ChartConfiguration();
    }

    @RequestMapping(value={"/jsonData"}, method = RequestMethod.POST)
    @ResponseBody
    public ArrayList<Series> getJsonData(@RequestBody RequestDataOptions requestDataOptions) {

        switch (requestDataOptions.getType()) {
            case 'd':
                this.chartConfiguration.setChart(new MultiChart(chartService));
                return this.chartConfiguration.getData(requestDataOptions);
            default:
                return null;
        }
    }

}
