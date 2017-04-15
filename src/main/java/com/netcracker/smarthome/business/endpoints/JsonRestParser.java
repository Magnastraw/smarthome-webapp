package com.netcracker.smarthome.business.endpoints;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netcracker.smarthome.business.endpoints.jsonentities.*;
import org.codehaus.jackson.JsonParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JsonRestParser {
    private static final Logger LOG = LoggerFactory.getLogger(JsonRestParser.class);
    private ObjectMapper objectMapper;

    public JsonRestParser() {
        this.objectMapper = new ObjectMapper();
    };

    public Map<String,JsonParameter> parseParameters(String json) {
        //List<ParameterMap> parameters = new ArrayList<ParameterMap>();
        //List<Map<String,JsonParameter>> parameters = new ArrayList<Map<String, JsonParameter>>();
        //JavaType javaType = objectMapper.getTypeFactory().constructCollectionType(List.class, objectMapper.getTypeFactory().constructParametricType(Map.class, String.class, JsonParameter.class));
        Map<String,JsonParameter> parameters = new LinkedHashMap<String, JsonParameter>();
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(Map.class, String.class, JsonParameter.class);
        try {
            parameters = objectMapper.readValue(json, javaType);
        }
        catch (JsonParseException ex) {
            LOG.error("Error during parsing of json: ", ex);
        }
        catch (IOException ex) {
            LOG.error("Error during parsing of json: ", ex);
        }
        catch (Exception ex) {
            LOG.error("Error during parsing of json: ", ex);
        }
        return parameters;
    }

    public List<JsonInventoryObject> parseInventory(String json) {
        List<JsonInventoryObject> inventoryObjects = new ArrayList<JsonInventoryObject>();
        try {
            inventoryObjects = objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, JsonInventoryObject.class));
        }
        catch (JsonParseException ex) {
            LOG.error("Error during parsing of json: ", ex);
        }
        catch (IOException ex) {
            LOG.error("Error during parsing of json: ", ex);
        }
        catch (Exception ex) {
            LOG.error("Error during parsing of json: ", ex);
        }
        return inventoryObjects;
    }

    public List<JsonAlarm> parseAlarms(String json) {
        List<JsonAlarm> alarmList = new ArrayList<JsonAlarm>();
        try {
            alarmList = objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, JsonAlarm.class));
        }
        catch (JsonParseException ex) {
            LOG.error("Error during parsing of json: ", ex);
        }
        catch (IOException ex) {
           LOG.error("Error during parsing of json: ", ex);
        }
        catch (Exception ex) {
            LOG.error("Error during parsing of json: ", ex);
        }
        return alarmList;
    }

    public List<JsonMetric> parseMetrics(String json) {
        List<JsonMetric> metricList = new ArrayList<JsonMetric>();
        try {
            metricList = objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, JsonMetric.class));
        }
        catch (JsonParseException ex) {
            LOG.error("Error during parsing of json: ", ex);
        }
        catch (IOException ex) {
            LOG.error("Error during parsing of json: ", ex);
        }
        catch (Exception ex) {
            LOG.error("Error during parsing of json: ", ex);
        }
        return metricList;
    }

    public List<JsonEvent> parseEvents(String json) {
        List<JsonEvent> eventList = new ArrayList<JsonEvent>();
        try {
            eventList = objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, JsonEvent.class));
        }
        catch (JsonParseException ex) {
            LOG.error("Error during parsing of json: ", ex);
        }
        catch (IOException ex) {
            LOG.error("Error during parsing of json: ", ex);
        }
        catch (Exception ex) {
            LOG.error("Error during parsing of json: ", ex);
        }
        return eventList;
    }

}
