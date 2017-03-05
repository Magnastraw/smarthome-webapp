package com.netcracker.smarthome.web.auth;

import com.netcracker.smarthome.business.auth.RegistryService;
import com.netcracker.smarthome.business.auth.UserExistsException;
import com.netcracker.smarthome.model.entities.User;
import com.netcracker.smarthome.web.common.ContextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;

@ManagedBean
@ViewScoped
public class RegistrationBean implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(RegistrationBean.class);

    @ManagedProperty(value = "#{registryService}")
    private RegistryService registryService;

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

    public void setRegistryService(RegistryService registryService) {
        this.registryService = registryService;
    }

    public void save() {
        String password = user.getEncrPassword();
        try {
            registryService.simpleRegister(user);
            loginAfterSuccessRegistration(user.getEmail(), password);
        } catch (UserExistsException e) {
            logger.error("Error during register new user", e);
            ContextUtils.addErrorMessageToContext(e.getMessage());
        } catch (IOException e) {
            logger.error("Login after registration failed", e);
            ContextUtils.addErrorMessageToContext(e.getMessage());
        }
    }

    private void loginAfterSuccessRegistration(String username, String password) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().dispatch(String.format("/login?email=%s&password=%s&remember_input=on", username, password));
        FacesContext.getCurrentInstance().responseComplete();
    }
}
