package com.netcracker.smarthome.business.endpoints;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netcracker.smarthome.business.endpoints.jsonentities.*;
import org.codehaus.jackson.JsonParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonRestParser {
    private static final Logger LOG = LoggerFactory.getLogger(JsonRestParser.class);
    private ObjectMapper objectMapper;

    private enum InventoryField {
        objectId,
        objectName,
        objectType,
        parentId,
        parameters
    }

    private enum ParameterField {
        value,
        type
    }

    public JsonRestParser() {
        this.objectMapper = new ObjectMapper();
    }

    public List<JsonParameter> parseParameters(JsonParser jsonParser) {
        List<JsonParameter> parameters = new ArrayList<JsonParameter>();
        try {
            while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                String paramName = jsonParser.getCurrentName();
                if (null == paramName) {
                    continue;
                } else {
                    jsonParser.nextToken(); // {
                    JsonParameter parameter = new JsonParameter(paramName);
                    while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
                        String paramFieldName = jsonParser.getCurrentName();
                        ParameterField paramField;
                        if (null == paramFieldName) {
                            continue;
                        } else {
                            paramField = ParameterField.valueOf(paramFieldName);
                            switch (paramField) {
                                case value:
                                    jsonParser.nextToken();
                                    parameter.setValue(jsonParser.getText());
                                    break;
                                case type:
                                    jsonParser.nextToken();
                                    parameter.setType(jsonParser.getText());
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                    parameters.add(parameter);
                }
            }
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
        JsonFactory jsonFactory = new JsonFactory();
        try {
            JsonParser jsonParser = jsonFactory.createParser(json);
            while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                JsonInventoryObject item = new JsonInventoryObject();
                while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
                    String fieldName = jsonParser.getCurrentName();
                    InventoryField field;
                    if (null == fieldName) {
                        continue;
                    } else {
                        field = InventoryField.valueOf(fieldName);
                    }
                    switch (field) {
                        case objectId:
                            jsonParser.nextToken();
                            item.setObjectId(jsonParser.getLongValue());
                            break;
                        case objectName:
                            jsonParser.nextToken();
                            item.setObjectName(jsonParser.getText());
                            break;
                        case objectType:
                            jsonParser.nextToken();
                            item.setObjectType(jsonParser.getText());
                            break;
                        case parentId:
                            jsonParser.nextToken();
                            item.setParentId(jsonParser.getLongValue());
                            break;
                        case parameters:
                            jsonParser.nextToken(); // [
                            item.setParameters(parseParameters(jsonParser));
                            break;
                        default:
                            break;
                    }
                }
                inventoryObjects.add(item);
            }
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
