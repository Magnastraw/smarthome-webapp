package com.netcracker.smarthome.web;

import com.netcracker.smarthome.web.common.ContextUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.WebAttributes;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.Serializable;

@ManagedBean
@RequestScoped
public class LoginBean implements Serializable {
    public String login() throws ServletException, IOException {
        ExternalContext context = FacesContext.getCurrentInstance()
                .getExternalContext();
        RequestDispatcher dispatcher = ((ServletRequest) context.getRequest()).getRequestDispatcher("/login");
        dispatcher.forward((ServletRequest) context.getRequest(),
                (ServletResponse) context.getResponse());
        FacesContext.getCurrentInstance().responseComplete();
        return null;
    }

    public void checkError() {
        Exception e = (Exception) FacesContext.getCurrentInstance().
                getExternalContext().getSessionMap().get(WebAttributes.AUTHENTICATION_EXCEPTION);
        if (e instanceof BadCredentialsException) {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(
                    WebAttributes.AUTHENTICATION_EXCEPTION, null);
            ContextUtils.addErrorMessageToContext("Incorrect credentials!");
        }
    }
}
