package com.netcracker.smarthome.web;

import com.netcracker.smarthome.business.auth.AuthService;
import com.netcracker.smarthome.model.entities.User;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;

@ManagedBean(eager = true)
@SessionScoped
public class UserAccountBean implements Serializable {
    private final String TEMPLATE_PAGE = "/faces/template/template";
    private final String LOGIN_PAGE = "/faces/auth/login";

    @ManagedProperty(value = "#{authService}")
    private AuthService authService;

    private String email, password;
    private User currentUser;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public String login() {
        if ((currentUser = authService.authenticateByCredentials(email, password)) == null) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!",
                    "Incorrect credentials!"));
            return null;
        }
        return TEMPLATE_PAGE + "?faces-redirect=true";
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return LOGIN_PAGE + "?faces-redirect=true";
    }
}
