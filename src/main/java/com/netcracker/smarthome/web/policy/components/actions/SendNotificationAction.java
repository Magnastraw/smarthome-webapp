package com.netcracker.smarthome.web.policy.components.actions;

import com.netcracker.smarthome.web.policy.components.UIAction;
import com.netcracker.smarthome.web.policy.components.UIParameter;

import java.util.HashMap;
import java.util.Map;

public class SendNotificationAction implements UIAction {
    protected Map<String, UIParameter> templateParams;

    public SendNotificationAction(Map<String, String> params) {
        templateParams = new HashMap<>();
        initParamsMap(params);
    }

    protected void initParamsMap(Map<String, String> params) {
        templateParams.put("@message", new UIParameter(params.get("message")));
    }

    @Override
    public String getTemplate() {
        return "Send notification @message";
    }

    @Override
    public Map<String, UIParameter> getTemplateParameters() {
        return templateParams;
    }
}
