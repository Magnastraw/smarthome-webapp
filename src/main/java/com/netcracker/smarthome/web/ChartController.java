package com.netcracker.smarthome.web;

import com.netcracker.smarthome.business.chart.ChartService;
import com.netcracker.smarthome.model.entities.MetricHistory;
import com.netcracker.smarthome.model.entities.SmartObject;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;

@Controller
public class ChartController {
    @Autowired
    ChartService chartService;

    @RequestMapping({"/jsonData/{to}/{id}"})
    @ResponseBody
    public String getJsonData(@PathVariable long id, @PathVariable String to) {
        JSONArray resultArray = new JSONArray();
        if (to.equals("metric")) { //плохой подход
            for (SmartObject smartObject : chartService.getSmartObjectBySpecId(id)) {
                JSONObject dataObject = new JSONObject();
                JSONArray dataArray = new JSONArray();
                for (MetricHistory metricHistory : chartService.getMetricsHistoryBySpecIdObjectID(id, smartObject.getSmartObjectId())) {
                    dataArray.put(new JSONObject().put("x", new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(metricHistory.getReadDate()))
                            .put("y", metricHistory.getValue()));
                }
                dataObject.put("data", dataArray);
                dataObject.put("name", smartObject.getName());
                dataObject.put("type", "spline");
                resultArray.put(dataObject);
            }
        }
        return resultArray.toString();
    }
}
