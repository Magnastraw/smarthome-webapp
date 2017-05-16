package com.netcracker.smarthome.web.profile;

import com.netcracker.smarthome.dal.repositories.UserRepository;
import com.netcracker.smarthome.model.entities.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.apache.commons.codec.digest.DigestUtils;
import org.primefaces.context.RequestContext;

@ManagedBean
@ViewScoped
public class ProfileBean {

    @ManagedProperty("#{userRepository}")
    private UserRepository userRepository;

    private String oldPassword;
    private String newPassword;
    private String newPasswordRetype;
    private User user;
    private Map<String, String> params;
    private Map<String, String> editedParams;
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

    public Map<String, String> getParams() {
        return params;
    }

    public Map<String, String> getEditedParams() {
        return editedParams;
    }

    public List<String> getKeys() {
        return keys;
    }

    public String getPreferChannel() {
        return user.getPreferChannel().toString();
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
        editedParams=new HashMap<>(params);
        keys = new ArrayList<String>(params.keySet());
    }

    public void editPersonalData() {
        boolean ok_data;
        try {
            user.setFirstName(editedParams.get("First name"));
            user.setLastName(editedParams.get("Last name"));
            user.setPhoneNumber(editedParams.get("Phone number"));
            userRepository.update(user);
            ok_data = true;
        } catch (Exception e) {
            ok_data = false;
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.addCallbackParam("ok_data", ok_data);
    }

    public void changePassword(ActionEvent actionEvent) {
        boolean ok_pwd;
        String pwd = DigestUtils.md5Hex(oldPassword);
        if (pwd.equals(user.getEncrPassword())) {
            ok_pwd = true;
            user.setEncrPassword(DigestUtils.md5Hex(newPassword));
            userRepository.update(user);
        } else {
            ok_pwd = false;
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.addCallbackParam("ok_pwd", ok_pwd);
    }
}
