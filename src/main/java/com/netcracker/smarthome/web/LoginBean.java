package com.netcracker.smarthome.web;

import com.netcracker.smarthome.business.notification.NotificationService;
import com.netcracker.smarthome.business.notification.SMS;
import com.netcracker.smarthome.dao.repositories.UserRepository;
import com.netcracker.smarthome.model.entities.Notification;
import com.netcracker.smarthome.model.entities.User;
import com.netcracker.smarthome.model.enums.Channel;
import com.netcracker.smarthome.model.enums.NotificationStatus;
import com.netcracker.smarthome.web.common.ContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Time;
import java.time.LocalTime;

@ManagedBean
@Component
@Scope(value = "request")
public class LoginBean implements Serializable {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationService notificationService;

    public String login() throws ServletException, IOException {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        RequestDispatcher dispatcher = ((ServletRequest) context.getRequest()).getRequestDispatcher("/login");
        dispatcher.forward((ServletRequest) context.getRequest(), (ServletResponse) context.getResponse());
        FacesContext.getCurrentInstance().responseComplete();
        User user = userRepository.getByEmail(context.getUserPrincipal().getName());

        Notification notification = notificationService.createNotification("User login", NotificationStatus.NOT_RESOLVED,
                Time.valueOf(LocalTime.now()), false, Channel.PHONE,
                user, null, null, null);
        notificationService.sendSMS(notification);

        return null;
    }

    public void checkError() {
        Exception e = (Exception) FacesContext.getCurrentInstance().
                getExternalContext().getSessionMap().get(WebAttributes.AUTHENTICATION_EXCEPTION);
        if (e instanceof BadCredentialsException) {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(WebAttributes.AUTHENTICATION_EXCEPTION, null);
            ContextUtils.addErrorMessageToContext("Incorrect credentials!");
        }
    }
}
