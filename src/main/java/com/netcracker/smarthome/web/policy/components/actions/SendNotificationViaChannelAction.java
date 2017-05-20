package com.netcracker.smarthome.web.policy.components.actions;

import com.netcracker.smarthome.web.policy.components.UIParameter;

import java.util.Map;

public class SendNotificationViaChannelAction extends SendNotificationAction {
    public SendNotificationViaChannelAction(Map<String, String> params) {
        super(params);
    }

    @Override
    protected void initParamsMap(Map<String, String> params) {
        super.initParamsMap(params);
        templateParams.put("@channel", new UIParameter(params.get("preferredChannel").toUpperCase()));
    }

    @Override
    public String getTemplate() {
        return "Send notification @message via @channel";
    }
}
