package com.netcracker.smarthome.business.endpoints;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netcracker.smarthome.business.endpoints.jsonentities.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JsonRestParser {
    private ObjectMapper objectMapper;

    public JsonRestParser() {
        this.objectMapper = new ObjectMapper();
    }

    public Map<String,JsonParameter> parseParameters(String json) throws IOException {
        //List<ParameterMap> parameters = new ArrayList<ParameterMap>();
        //List<Map<String,JsonParameter>> parameters = new ArrayList<Map<String, JsonParameter>>();
        //JavaType javaType = objectMapper.getTypeFactory().constructCollectionType(List.class, objectMapper.getTypeFactory().constructParametricType(Map.class, String.class, JsonParameter.class));
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(Map.class, String.class, JsonParameter.class);
        Map<String,JsonParameter> parameters = objectMapper.readValue(json, javaType);
        return parameters;
    }

    public List<JsonInventoryObject> parseInventory(String json) throws IOException {
        List<JsonInventoryObject> inventoryObjects = objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, JsonInventoryObject.class));
        return inventoryObjects;
    }

    public JsonInventoryObject parseInventoryObject(String json) throws IOException {
        JsonInventoryObject inventoryObject = objectMapper.readValue(json, JsonInventoryObject.class);
        return inventoryObject;
    }

    public List<JsonAlarm> parseAlarms(String json) throws IOException {
        List<JsonAlarm> alarmList = objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, JsonAlarm.class));
        return alarmList;
    }

    public List<JsonMetric> parseMetrics(String json) throws IOException {
        List<JsonMetric> metricList = objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, JsonMetric.class));
        return metricList;
    }

    public List<JsonEvent> parseEvents(String json) throws IOException {
        List<JsonEvent> eventList = objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, JsonEvent.class));
        return eventList;
    }

}
