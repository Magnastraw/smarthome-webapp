package com.netcracker.smarthome.web.profile;

import com.netcracker.smarthome.dal.repositories.UserRepository;
import com.netcracker.smarthome.model.entities.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
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
    private HashMap<String, String> params;
    private List<String> keys;

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

    public HashMap<String, String> getParams() {
        return params;
    }

    public List<String> getKeys() {
        return keys;
    }

    private void createMapParams() {
        params = new HashMap<>();
        params.put("First name", user.getFirstName());
        params.put("Last name", user.getLastName());
        params.put("Phone number", user.getPhoneNumber());
    }

    @PostConstruct
    public void init() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String userLogin = facesContext.getExternalContext().getRemoteUser();
        user = userRepository.getByEmail(userLogin);
        createMapParams();
        keys = new ArrayList<String>(params.keySet());
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
