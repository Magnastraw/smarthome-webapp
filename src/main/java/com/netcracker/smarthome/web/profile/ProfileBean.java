package com.netcracker.smarthome.web.profile;

import com.netcracker.smarthome.dal.repositories.UserRepository;
import com.netcracker.smarthome.model.entities.User;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.apache.commons.codec.digest.DigestUtils;
import org.primefaces.context.RequestContext;

@ManagedBean
@SessionScoped
public class ProfileBean {

    @ManagedProperty("#{userRepository}")
    private UserRepository userRepository;

    private String oldPassword;
    private String newPassword;
    private String newPasswordRetype;
    private User user;

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPasswordRetype() {
        return newPasswordRetype;
    }

    public void setNewPasswordRetype(String newPasswordRetype) {
        this.newPasswordRetype = newPasswordRetype;
    }

    public String getFirstName() {
        return user.getFirstName();
    }

    public String getLastName() {
        return user.getLastName();
    }

    public String getPhoneNumber() {
        return user.getPhoneNumber();
    }

    @PostConstruct
    public void init() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String userLogin = facesContext.getExternalContext().getRemoteUser();
        user = userRepository.getByEmail(userLogin);
    }

    public void changePassword() {
        boolean ok;
        String pwd = DigestUtils.md5Hex(oldPassword);
        if (pwd.equals(user.getEncrPassword())) {
            ok = true;
            user.setEncrPassword(DigestUtils.md5Hex(newPassword));
            userRepository.update(user);
        } else {
            ok = false;
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.addCallbackParam("ok", ok);
    }
}
