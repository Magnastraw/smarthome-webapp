package com.netcracker.smarthome.web;

import com.netcracker.smarthome.business.HomeService;
import com.netcracker.smarthome.business.auth.AuthService;
import com.netcracker.smarthome.model.entities.User;

import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;

@ManagedBean
@ViewScoped
public class RegistrationBean implements Serializable {
    private final String TEMPLATE_PAGE = "/faces/template/template";

    @ManagedProperty(value = "#{authService}")
    private AuthService authService;
    @ManagedProperty(value = "#{homeService}")
    private HomeService homeService;

    private User user;

    public RegistrationBean() {
        user = new User();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

    public void setHomeService(HomeService homeService) {
        this.homeService = homeService;
    }

    public String save() {
        if (!authService.register(user)) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!",
                    "User with such email already exists!"));
            return null;
        }
        homeService.createDefaultHome(user);
        login();
        return TEMPLATE_PAGE + "?faces-redirect=true";
    }

    private void login() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        UserAccountBean bean = (UserAccountBean) elContext.getELResolver().getValue(elContext, null, "userAccountBean");
        if (bean != null)
            bean.setCurrentUser(user);
    }
}
