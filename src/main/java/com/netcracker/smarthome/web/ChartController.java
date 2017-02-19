package com.netcracker.smarthome.web;

import com.netcracker.smarthome.business.chart.ChartService;
import com.netcracker.smarthome.model.entities.*;
import com.netcracker.smarthome.web.common.ContextUtils;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
public class ChartController {

    @Autowired
    ChartService chartService;


    @RequestMapping({"/jsonData/{homeId}/obj/{objectId}/subObj/{subObjectId}/metric/{metricSpecId}"})
    @ResponseBody
    public String getJsonData(@PathVariable long homeId, @PathVariable long objectId, @PathVariable long subObjectId, @PathVariable long metricSpecId) {
        User user = ContextUtils.getCurrentUser();
        JSONArray resultArray = new JSONArray();
        for (SmartObject smartObject : chartService.getSmartObjectBySpecId(user.getUserId(), homeId, metricSpecId)) {
            JSONObject dataObject = new JSONObject();
            JSONArray dataArray = new JSONArray();
            for (Object[] metricHistory : chartService.getMetricsHistoryBySpecIdObjectId(user.getUserId(), homeId, metricSpecId, smartObject.getSmartObjectId())) {
                Timestamp readDate = (Timestamp) metricHistory[1];
                BigDecimal value = (BigDecimal) metricHistory[2];
                dataArray.put(new JSONObject().put("x", new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(readDate))
                        .put("y", value));
            }
            dataObject.put("data", dataArray);
            dataObject.put("name", smartObject.getName());
            dataObject.put("type", "spline");
            resultArray.put(dataObject);
        }

//        for (MetricSpec metricSpec : chartService.getMetricsSpecByObjectId(user.getUserId(),objectId)){
//            JSONObject dataObject = new JSONObject();
//            JSONArray dataArray = new JSONArray();
//            for (MetricHistory metricHistory : chartService.getMetricsHistoryBySpecIdObjectIdUserId(user.getUserId(),metricSpec.getSpecId(), objectId)) {
//                dataArray.put(new JSONObject().put("x", new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(metricHistory.getReadDate()))
//                        .put("y", metricHistory.getValue()));
//            }
//            dataObject.put("data", dataArray);
//            dataObject.put("name", metricSpec.getSpecName());
//            dataObject.put("type", "spline");
//            resultArray.put(dataObject);
//        }

        return resultArray.toString();
    }
}
