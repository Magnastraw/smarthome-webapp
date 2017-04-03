package com.netcracker.smarthome.business.policy.actions;

import com.netcracker.smarthome.model.entities.SmartHome;
import com.netcracker.smarthome.model.enums.Channel;

public class SendNotificationAction implements Action {
    private String message;
    private Channel preferredChannel;
    private SmartHome smartHome;

    public void execute(ActionParams params) {
        // TODO: add notification sending via NotificationService API
    }
}
