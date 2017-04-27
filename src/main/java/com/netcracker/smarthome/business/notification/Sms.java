package com.netcracker.smarthome.business.notification;

import com.netcracker.smarthome.model.entities.*;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class Sms implements NotificationSender {

    private final String ACCOUNT_SID = "AC20dcdfe24e0b299b228418bb529fd41e";
    private final String AUTH_TOKEN = "dda60187c70c1492fe62bb1475fe1068";
    private final PhoneNumber serverNumber = new PhoneNumber("+14693363831");
    private Message message = null;

    public void sendNotification(Notification notification) {
        User user = notification.getSmartHome().getUser();
        PhoneNumber userNumber = new PhoneNumber(user.getPhoneNumber());
        try {
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            message = Message.creator(userNumber, serverNumber, notification.getNotificationName()).create();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getStatus(){
        return null;
    }
}
