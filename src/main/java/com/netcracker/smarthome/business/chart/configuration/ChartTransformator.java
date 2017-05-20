package com.netcracker.smarthome.business.chart.configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netcracker.smarthome.business.endpoints.transformators.ITransformator;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class ChartTransformator implements ITransformator<HighchartConfig, ChartOptionsContainer> {
    private ObjectMapper objectMapper;
    private VelocityContext vc;
    private Map<String,String> templateMap;
    private static final Logger LOG = LoggerFactory.getLogger(ChartTransformator.class);

    public ChartTransformator() {
        this.objectMapper = new ObjectMapper();
        this.templateMap = new HashMap<String, String>();
        templateMap.put("area","velocityTemplates/default.vm");
        templateMap.put("line","velocityTemplates/default.vm");
        templateMap.put("column","velocityTemplates/default.vm");
        Velocity.init(this.getClass().getClassLoader().getResource("/velocity.properties").getPath());
        vc = new VelocityContext();
    }

    @Override
    public HighchartConfig fromJsonEntity(ChartOptionsContainer jsonEntity) {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public ChartOptionsContainer toJsonEntity(HighchartConfig entity)  {
        ChartOptionsContainer chartOptionsContainer= new ChartOptionsContainer();
        vc.put("chartOption", entity.getChartOptions());
        Template t = Velocity.getTemplate(templateMap.get( entity.getChartOptions().getType()), "utf-8");
        try {
            StringWriter sw = new StringWriter();
            t.merge(vc, sw);
            chartOptionsContainer.setJsonChartConfig(sw.toString());
            sw.close();
        } catch (IOException ex){
            LOG.error("Error during velocity merge", ex);
        }

        try {
            chartOptionsContainer.setJsonRequestOptions(objectMapper.writeValueAsString(entity.getRequestDataOptions()));
        } catch (JsonProcessingException ex){
            LOG.error("Error during requestData json transform", ex);
        }

        chartOptionsContainer.setRefreshInterval(entity.getRefreshInterval());

        return chartOptionsContainer;
    }
}
