package com.netcracker.smarthome.web.policy.components.conditions;

import com.netcracker.smarthome.web.policy.components.UIParameter;

import java.util.Map;

public class InlineEventConditionWithSeverity extends InlineEventCondition {
    public InlineEventConditionWithSeverity(Map<String, String> params) {
        super(params);
    }

    @Override
    protected void initParamsMap(Map<String, String> params) {
        super.initParamsMap(params);
        templateParams.put("@severity", new UIParameter(params.get("severity").toUpperCase()));
    }

    @Override
    public String getTemplate() {
        return "@event_type with specification @spec and severity @severity on object @object";
    }
}
