package com.netcracker.smarthome.web.policy.components.conditions;

import com.netcracker.smarthome.ApplicationContextHolder;
import com.netcracker.smarthome.dal.repositories.MetricSpecRepository;
import com.netcracker.smarthome.model.entities.MetricSpec;
import com.netcracker.smarthome.web.policy.components.UICondition;
import com.netcracker.smarthome.web.policy.components.UIParameter;

import java.util.HashMap;
import java.util.Map;

public abstract class MetricCondition implements UICondition {
    protected Map<String, UIParameter> templateParams;

    public MetricCondition(Map<String, String> params) {
        this.templateParams = new HashMap<>();
        initParamsMap(params);
    }

    protected void initParamsMap(Map<String, String> params) {
        MetricSpecRepository metricSpecRepository = ApplicationContextHolder.getApplicationContext().getBean(MetricSpecRepository.class);
        MetricSpec metricSpec = metricSpecRepository.get(Long.parseLong(params.get("metric")));
        templateParams.put("@metric", new UIParameter(metricSpec.getSpecName()));
    }

    @Override
    public Map<String, UIParameter> getTemplateParameters() {
        return templateParams;
    }
}
