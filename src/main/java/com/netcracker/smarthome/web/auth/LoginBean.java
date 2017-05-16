package com.netcracker.smarthome.web.auth;

import com.netcracker.smarthome.business.auth.social.SocialServiceClient;
import com.netcracker.smarthome.business.services.NotificationService;
import com.netcracker.smarthome.dal.repositories.SmartHomeRepository;
import com.netcracker.smarthome.model.entities.SmartHome;
import com.netcracker.smarthome.model.enums.Channel;
import com.netcracker.smarthome.web.common.ContextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

@ManagedBean
@Scope(value = "request")
@Component
public class LoginBean implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(LoginBean.class);

    @Autowired
    private SmartHomeRepository smartHomeRepository;
    @Autowired
    private NotificationService notificationService;

    private boolean remember = true;

    public boolean isRemember() {
        return remember;
    }

    public void setRemember(boolean remember) {
        this.remember = remember;
    }

    public void redirect(String serviceName) {
        String redirectUri = getRedirectUri(serviceName);
        try {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("remember", remember);
            FacesContext.getCurrentInstance().getExternalContext().redirect(redirectUri);
        } catch (IOException e) {
            logger.error("Error during redirect to social service", e);
        }
    }

    public void login() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().dispatch("/login");
            FacesContext.getCurrentInstance().responseComplete();
        } catch (IOException e) {
            logger.error("Error during dispatching login request", e);
        }
    }

    public void checkError() {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        Exception e = (Exception) sessionMap.get(WebAttributes.AUTHENTICATION_EXCEPTION);
        if (e instanceof BadCredentialsException) {
            ContextUtils.addErrorMessageToContext("Incorrect credentials!");
            sessionMap.remove(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
        String oauthExcMsg = (String) sessionMap.get("oauthExc");
        if (oauthExcMsg != null) {
            ContextUtils.addErrorMessageToContext(oauthExcMsg);
            sessionMap.remove("oauthExc");
        }
    }

    private String getRedirectUri(String serviceName) {
        SocialServiceClient serviceClient = (SocialServiceClient) ContextUtils.getBean(serviceName + "Client");
        return serviceClient.buildServiceRedirectUrl(getSocialLoginPath(serviceName));
    }

    private String getSocialLoginPath(String serviceName) {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        return String.format("%s://%s:%d%s/social_login/%s",
                context.getRequestScheme(),
                context.getRequestServerName(),
                context.getRequestServerPort(),
                context.getRequestContextPath(),
                serviceName);
    }
}
