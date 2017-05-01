package com.netcracker.smarthome.business.chart.configuration;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netcracker.smarthome.business.chart.options.ChartOptions;
import com.netcracker.smarthome.business.chart.options.RequestDataOptions;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class ChartConfigImpl implements ChartConfig {
    private static final Logger LOG = LoggerFactory.getLogger(ChartConfigImpl.class);

    private String jsonChartConfig;
    private long chartId;
    private ChartOptions chartOptions;
    private RequestDataOptions requestDataOptions;
    private long refreshInterval;
    private ObjectMapper objectMapper;
    private VelocityContext vc;
    private Map<String,String> templateMap;

    public ChartConfigImpl(ChartOptions chartOptions, RequestDataOptions requestDataOptions) {
        this.chartOptions = chartOptions;
        this.requestDataOptions = requestDataOptions;
        this.objectMapper = new ObjectMapper();
        this.templateMap = new HashMap<String, String>();
        templateMap.put("area","velocityTemplates/default.vm");
        templateMap.put("line","velocityTemplates/default.vm");
        templateMap.put("column","velocityTemplates/default.vm");
        Velocity.init(this.getClass().getClassLoader().getResource("/velocity.properties").getPath());
        vc = new VelocityContext();
    }

    public String getJsonChartConfig() throws IOException {
        vc.put("chartOption", chartOptions);
        Template t = Velocity.getTemplate(templateMap.get(chartOptions.getType()), "utf-8");
        StringWriter sw = new StringWriter();
        t.merge(vc, sw);
        jsonChartConfig = sw.toString();
        sw.close();
        LOG.info("Chart options:" + jsonChartConfig);
        return jsonChartConfig;
    }

    public String getJsonRequestDataConfig() throws JsonProcessingException {
        return objectMapper.writeValueAsString(requestDataOptions);
    }

    public long getChartId() {
        return chartId;
    }

    public void setChartId(long chartId) {
        this.chartId = chartId;
    }

    public ChartOptions getChartOptions() {
        return chartOptions;
    }

    public void setChartOptions(ChartOptions chartOptions) {
        this.chartOptions = chartOptions;
    }

    public RequestDataOptions getRequestDataOptions() {
        return requestDataOptions;
    }

    public void setRequestDataOptions(RequestDataOptions requestDataOptions) {
        this.requestDataOptions = requestDataOptions;
    }

    public long getRefreshInterval() {
        return refreshInterval;
    }

    public void setRefreshInterval(long refreshInterval) {
        this.refreshInterval = refreshInterval;
    }
}
