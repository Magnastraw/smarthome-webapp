package com.netcracker.smarthome.business.notification;

import com.netcracker.smarthome.model.entities.Notification;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email implements NotificationSender {

    public void sendNotification(Notification notification) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("smarthome.netcracker", "rootroot");
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("smarthome.netcracker@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(notification.getSmartHome().getUser().getEmail()));
            message.setSubject("SmartHome");
            message.setText(notification.getNotificationName());
            Transport.send(message, message.getRecipients(Message.RecipientType.TO));
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public String getStatus() {
        return null;
    }
}
