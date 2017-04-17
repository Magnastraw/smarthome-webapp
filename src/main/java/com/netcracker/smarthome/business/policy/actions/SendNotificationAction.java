package com.netcracker.smarthome.business.policy.actions;

import com.netcracker.smarthome.business.policy.events.Event;
import com.netcracker.smarthome.model.enums.Channel;

import java.util.HashMap;

public class SendNotificationAction implements Action {
    private String message;
    private Channel preferredChannel;
    private long smartHome;

    public SendNotificationAction(HashMap<String, String> params) {
        message = params.get("message");
        preferredChannel = Channel.valueOf(params.get("prefferedChannel").toUpperCase());
        smartHome = Long.parseLong(params.get("smartHome"));
    }

    public void execute(Event causalEvent) {
        // TODO: add notification sending via NotificationService API
    }
}
