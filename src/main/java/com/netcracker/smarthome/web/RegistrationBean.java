package com.netcracker.smarthome.web;

import com.netcracker.smarthome.business.HomeService;
import com.netcracker.smarthome.business.auth.RegisterService;
import com.netcracker.smarthome.business.auth.UserExistsException;
import com.netcracker.smarthome.business.auth.security.CustomUserDetailsService;
import com.netcracker.smarthome.model.entities.User;
import com.netcracker.smarthome.web.common.ContextUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import java.io.Serializable;

@ManagedBean
@ViewScoped
public class RegistrationBean implements Serializable {
    private final String TEMPLATE_PAGE = "/faces/template/template";
    private final String LOGIN_PAGE = "/faces/auth/login";

    @ManagedProperty(value = "#{registerService}")
    private RegisterService registerService;

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

    public void setRegisterService(RegisterService registerService) {
        this.registerService = registerService;
    }

    public void setHomeService(HomeService homeService) {
        this.homeService = homeService;
    }

    public String save() {
        String password = user.getEncrPassword();
        try {
            registerService.simpleRegister(user);
        } catch (UserExistsException e) {
            ContextUtils.addErrorMessageToContext(e.getMessage());
            return null;
        }
        homeService.createDefaultHome(user);
        try {
            loginAfterSuccessRegistration(user.getEmail(), password);
        } catch (AuthenticationException e) {
            ContextUtils.addErrorMessageToContext("Login error after registration!");
            return LOGIN_PAGE;
        }
        return TEMPLATE_PAGE + "?faces-redirect=true";
    }

    private void loginAfterSuccessRegistration(String username, String password) throws AuthenticationException {
        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext());
        AuthenticationManager manager = (AuthenticationManager) context.getBean("authenticationManager");
        CustomUserDetailsService service = (CustomUserDetailsService) context.getBean("myUserDetailsService");
        Authentication auth = manager.authenticate(new UsernamePasswordAuthenticationToken(username, password, service.buildAuthorities()));
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}
