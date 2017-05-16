package com.netcracker.smarthome.web.profile;

import com.netcracker.smarthome.dal.repositories.UserRepository;
import com.netcracker.smarthome.model.entities.User;
import com.netcracker.smarthome.model.enums.Channel;
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
    private List<String> keys;
    private String preferChannel;
    private List<String> channels;

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

    public List<String> getKeys() {
        return keys;
    }

    public String getPreferChannel() {
        return preferChannel;
    }

    public void setPreferChannel(String preferChannel) {
        this.preferChannel = preferChannel;
    }

    public List<String> getChannels() {
        return channels;
    }

    private void createMapParams() {
        String firstName;
        String lastName;
        String phoneNumber;
        if (user.getFirstName() != null && !user.getFirstName().equals("")) {
            firstName = user.getFirstName();
        } else {
            firstName = "Type here";
        }
        if (user.getLastName() != null && !user.getLastName().equals("")) {
            lastName = user.getLastName();
        } else {
            lastName = "Type here";
        }
        if (user.getPhoneNumber() != null && !user.getPhoneNumber().equals("")) {
            phoneNumber = user.getPhoneNumber();
        } else {
            phoneNumber = "Type here";
        }
        params = new HashMap<>();
        params.put("First name", firstName);
        params.put("Last name", lastName);
        params.put("Phone number", phoneNumber);
    }

    @PostConstruct
    public void init() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String userLogin = facesContext.getExternalContext().getRemoteUser();
        user = userRepository.getByEmail(userLogin);
        createMapParams();
        keys = new ArrayList<String>(params.keySet());
        preferChannel = user.getPreferChannel().toString();
        channels = new ArrayList<>();
        for (Channel channel : Channel.values()) {
            channels.add(channel.toString());
        }
    }

    public void editPersonalData() {
        if (!params.get("First name").equals("Type here")) {
            user.setFirstName(params.get("First name"));
        } else {
            user.setFirstName("");
            params.put("First name", "Type here");
        }
        if (!params.get("Last name").equals("Type here")) {
            user.setLastName(params.get("Last name"));
        } else {
            user.setLastName("");
            params.put("Last name", "Type here");
        }
        if (!params.get("Phone number").equals("Type here")) {
            user.setPhoneNumber(params.get("Phone number"));
        } else {
            user.setPhoneNumber("");
            params.put("Phone number", "Type here");
        }
        userRepository.update(user);
    }

    public void changePreferChannel() {
        user.setPreferChannel(Channel.valueOf(preferChannel));
        userRepository.update(user);
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
