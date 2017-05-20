package com.netcracker.smarthome.web.policy.components.actions;

import com.netcracker.smarthome.ApplicationContextHolder;
import com.netcracker.smarthome.dal.repositories.AlarmSpecRepository;
import com.netcracker.smarthome.model.entities.AlarmSpec;
import com.netcracker.smarthome.web.policy.components.UIAction;
import com.netcracker.smarthome.web.policy.components.UIParameter;

import java.util.HashMap;
import java.util.Map;

public class RaiseAlarmAction implements UIAction {
    private Map<String, UIParameter> templateParams;

    public RaiseAlarmAction(Map<String, String> params) {
        templateParams = new HashMap<>();
        initParamsMap(params);
    }

    private void initParamsMap(Map<String, String> params) {
        AlarmSpecRepository alarmSpecRepository = ApplicationContextHolder.getApplicationContext().getBean(AlarmSpecRepository.class);
        AlarmSpec spec = alarmSpecRepository.get(Long.parseLong(params.get("spec")));
        templateParams.put("@spec", new UIParameter(spec.getSpecName()));
        templateParams.put("@severity", new UIParameter(params.get("severity").toUpperCase()));
    }

    @Override
    public String getTemplate() {
        return "Raise alarm with specification @spec and severity @severity";
    }

    @Override
    public Map<String, UIParameter> getTemplateParameters() {
        return templateParams;
    }
}
