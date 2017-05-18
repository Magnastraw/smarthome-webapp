package com.netcracker.smarthome.web.policy.components.conditions;

import com.netcracker.smarthome.ApplicationContextHolder;
import com.netcracker.smarthome.dal.repositories.AlarmSpecRepository;
import com.netcracker.smarthome.dal.repositories.SmartObjectRepository;
import com.netcracker.smarthome.model.entities.AlarmSpec;
import com.netcracker.smarthome.model.entities.SmartObject;
import com.netcracker.smarthome.web.policy.components.UICondition;
import com.netcracker.smarthome.web.policy.components.UIParameter;

import java.util.HashMap;
import java.util.Map;

public class EventCondition implements UICondition {
    protected Map<String, UIParameter> templateParams;

    public EventCondition(Map<String, String> params) {
        templateParams = new HashMap<>();
        initParamsMap(params);
    }

    protected void initParamsMap(Map<String, String> params) {
        AlarmSpecRepository alarmSpecRepository = ApplicationContextHolder.getApplicationContext().getBean(AlarmSpecRepository.class);
        AlarmSpec spec = alarmSpecRepository.get(Long.parseLong(params.get("spec")));
        templateParams.put("@event_type", new UIParameter(params.get("type").toUpperCase()));
        templateParams.put("@spec", new UIParameter(spec.getSpecName()));
    }

    @Override
    public String getTemplate() {
        return "@event_type with specification @spec";
    }

    @Override
    public Map<String, UIParameter> getTemplateParameters() {
        return templateParams;
    }
}
