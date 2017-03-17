package com.netcracker.smarthome.web.chart.rest;

import com.netcracker.smarthome.business.chart.ChartService;
import com.netcracker.smarthome.web.chart.options.PositionOptions;
import com.netcracker.smarthome.web.chart.options.jsonfields.DataSeries;
import com.netcracker.smarthome.web.chart.options.RequestDataOptions;
import com.netcracker.smarthome.web.chart.options.jsonfields.Series;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.util.ArrayList;

@Controller
public class ChartController {
    private static final Logger LOG = LoggerFactory.getLogger(ChartController.class);
    private ChartConfiguration chartConfiguration;

    @Autowired
    ChartService chartService;

    @PostConstruct
    private void init() {
        chartConfiguration = new ChartConfiguration();
    }

    @RequestMapping(value = {"/jsonData"}, method = RequestMethod.POST)
    @ResponseBody
    public ArrayList<Series> getJsonData(@RequestBody RequestDataOptions requestDataOptions) throws ParseException {
        LOG.info("request:" + requestDataOptions);
        this.chartConfiguration.setChart(new MultiChart(chartService));
        return this.chartConfiguration.getData(requestDataOptions);

    }

    @RequestMapping(value = {"/jsonPositionOption"}, method = RequestMethod.POST)
    @ResponseBody
    public String getPositionOptions(@RequestBody ArrayList<PositionOptions> positionOptionss) {
        LOG.info("request:" + positionOptionss.get(0).getId());
        return "Done";
    }

}
