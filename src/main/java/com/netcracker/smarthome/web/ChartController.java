package com.netcracker.smarthome.web;

import com.netcracker.smarthome.business.chart.ChartService;
import com.netcracker.smarthome.model.entities.*;
import com.netcracker.smarthome.web.common.ContextUtils;
import com.netcracker.smarthome.web.home.CurrentUserHomesBean;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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


    @RequestMapping(value = {"/jsonDataConfig/{homeId}/{type}/obj/{objectId}/subObj/{subObjectId}/metric/{metricSpecId}"}, method = RequestMethod.POST)
    @ResponseBody
    public String getJsonDataConfig(@PathVariable char type, @PathVariable long homeId, @PathVariable long objectId, @PathVariable long subObjectId, @PathVariable long metricSpecId) {
        User user = ContextUtils.getCurrentUser();
        //TO DO: smartHome
        JSONArray resultArray = new JSONArray();
        int yAxisNumber = 0;
        switch (type) {
            case 'm':
                for (SmartObject smartObject : chartService.getSmartObjectBySpecId(user.getUserId(), homeId, metricSpecId)) {
                    JSONObject series = new JSONObject();
                    series.put("data", new JSONArray());
                    series.put("name", smartObject.getName());
                    series.put("type", "line");
                    JSONObject title = new JSONObject();
                    title.put("title", new JSONObject().put("text", chartService.getMetricSpecById(metricSpecId).getSpecName()));
                    JSONObject yAxis = new JSONObject();
                    yAxis.put("labels", new JSONObject().put("format", "{value}" + chartService.getUnitBySpecId(user.getUserId(), homeId, metricSpecId).getLabel()))
                            .put("title", new JSONObject().put("text", chartService.getMetricSpecById(metricSpecId).getSpecName()));
                    resultArray.put(new JSONObject().put("series", series).put("title", title).put("yAxis", yAxis));
                }
                break;
            case 'o':
                for (MetricSpec metricSpec : chartService.getMetricsSpecByObjectId(user.getUserId(), homeId, objectId)) {
                    JSONObject series = new JSONObject();
                    series.put("data", new JSONArray());
                    series.put("name", metricSpec.getSpecName());
                    series.put("type", "line");
                    series.put("yAxis", yAxisNumber++);//порядок?
                    JSONObject title = new JSONObject();
                    title.put("title", new JSONObject().put("text", chartService.getObjectById(objectId).getName()));
                    JSONObject yAxis = new JSONObject();
                    yAxis.put("labels", new JSONObject().put("format", "{value}" + chartService.getUnitBySpecId(user.getUserId(), homeId, metricSpec.getSpecId()).getLabel()))
                            .put("title", new JSONObject().put("text", metricSpec.getSpecName()));
                    resultArray.put(new JSONObject().put("series", series).put("title", title).put("yAxis", yAxis));
                }
                break;
            //TO DO: subobject
        }
        return resultArray.toString();
    }

    @RequestMapping({"/jsonData/{homeId}/{type}/obj/{objectId}/subObj/{subObjectId}/metric/{metricSpecId}/rownum/{rownum}"})
    @ResponseBody
    public String getJsonData(@PathVariable char type, @PathVariable long homeId, @PathVariable long objectId, @PathVariable long subObjectId, @PathVariable long metricSpecId, @PathVariable int rownum) {
        User user = ContextUtils.getCurrentUser();
        //TO DO: smartHome
        JSONArray resultArray = new JSONArray();
        switch (type) {
            case 'm':
                for (SmartObject smartObject : chartService.getSmartObjectBySpecId(user.getUserId(), homeId, metricSpecId)) {
                    JSONObject dataObject = new JSONObject();
                    JSONArray dataArray = new JSONArray();
                    for (Object[] metricHistory : chartService.getMetricsHistoryBySpecIdObjectId(user.getUserId(), homeId, metricSpecId, smartObject.getSmartObjectId(), rownum)) {

                        Timestamp readDate = (Timestamp) metricHistory[1];
                        BigDecimal value = (BigDecimal) metricHistory[2];
                        dataArray.put(new JSONObject().put("x", new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(readDate))
                                .put("y", value));
                    }

                    dataObject.put("data", dataArray);
                    resultArray.put(dataObject);
                }
                break;
            case 'o':
                for (MetricSpec metricSpec : chartService.getMetricsSpecByObjectId(user.getUserId(), homeId, objectId)) {
                    JSONObject dataObject = new JSONObject();
                    JSONArray dataArray = new JSONArray();
                    for (Object[] metricHistory : chartService.getMetricsHistoryBySpecIdObjectId(user.getUserId(), homeId, metricSpec.getSpecId(), objectId, rownum)) {

                        Timestamp readDate = (Timestamp) metricHistory[1];
                        BigDecimal value = (BigDecimal) metricHistory[2];
                        dataArray.put(new JSONObject().put("x", new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(readDate))
                                .put("y", value));

                    }
                    dataObject.put("data", dataArray);
                    resultArray.put(dataObject);
                }
                break;
            //TO DO: subobject
        }
        return resultArray.toString();
    }

    @RequestMapping(value = {"/jsonDataLive/{homeId}/{type}/obj/{objectId}/subObj/{subObjectId}/metric/{metricSpecId}/rownum/{rownum}"}, method = RequestMethod.POST)
    @ResponseBody
    public String getJsonDataLive(@PathVariable char type, @PathVariable long homeId, @PathVariable long objectId, @PathVariable long subObjectId, @PathVariable long metricSpecId, @PathVariable int rownum) {
        User user = ContextUtils.getCurrentUser();
        //TO DO: smartHome
        JSONArray resultArray = new JSONArray();
        switch (type) {
            case 'm':
                for (SmartObject smartObject : chartService.getSmartObjectBySpecId(user.getUserId(), homeId, metricSpecId)) {
                    JSONObject dataObject = new JSONObject();
                    JSONArray dataArray = new JSONArray();
                    Object[] metricHistory = chartService.getMetricHistoryBySpecIdObjectId(user.getUserId(), homeId, metricSpecId, smartObject.getSmartObjectId(), rownum);
                    Timestamp readDate = (Timestamp) metricHistory[1];
                    BigDecimal value = (BigDecimal) metricHistory[2];
                    dataArray.put(new JSONObject().put("x", new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(readDate))
                            .put("y", value));
                    dataObject.put("data", dataArray);
                    resultArray.put(dataObject);
                }
                break;
            case 'o':
                for (MetricSpec metricSpec : chartService.getMetricsSpecByObjectId(user.getUserId(), homeId, objectId)) {
                    JSONObject dataObject = new JSONObject();
                    JSONArray dataArray = new JSONArray();
                    Object[] metricHistory = chartService.getMetricHistoryBySpecIdObjectId(user.getUserId(), homeId, metricSpec.getSpecId(), objectId, rownum);
                    Timestamp readDate = (Timestamp) metricHistory[1];
                    BigDecimal value = (BigDecimal) metricHistory[2];
                    dataArray.put(new JSONObject().put("x", new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(readDate))
                            .put("y", value));
                    dataObject.put("data", dataArray);
                    resultArray.put(dataObject);
                }
                break;
            //TO DO: subobject
        }
        return resultArray.toString();
    }

}
