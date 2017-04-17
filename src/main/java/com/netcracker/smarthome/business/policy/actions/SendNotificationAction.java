package com.netcracker.smarthome.business.policy.actions;

import com.netcracker.smarthome.business.policy.events.PolicyEvent;
import com.netcracker.smarthome.model.enums.Channel;

import java.util.HashMap;

public class SendNotificationAction implements Action {
    private String message;
    private Channel preferredChannel;

    public SendNotificationAction(HashMap<String, String> params) {
        message = params.get("message");
        preferredChannel = Channel.valueOf(params.get("preferredChannel"));
    }

    public void execute(PolicyEvent causalEvent) {
        // TODO: add notification sending via NotificationService API
    }
}
