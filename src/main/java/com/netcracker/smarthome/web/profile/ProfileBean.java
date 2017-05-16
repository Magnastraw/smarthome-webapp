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

    private User user;
    private Map<String, String> params;
    private List<String> keysData;
    private Map<String, String> pwdParams;
    private List<String> keysPwd;
    private Map<String, String> editedParams;
    private List<String> keys;
    private int typeDlg;

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public Map<String, String> getEditedParams() {
        return editedParams;
    }

    public Map<String, String> getPwdParams() {
        return pwdParams;
    }

    public List<String> getKeysData() {
        return keysData;
    }
    
    public List<String> getKeysPwd() {
        return keysPwd;
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

    private void createMapPwdParams() {
        pwdParams = new HashMap<>();
        pwdParams.put("Current password", "");
        pwdParams.put("New password", "");
        pwdParams.put("Retype new password", "");
    }

    @PostConstruct
    public void init() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String userLogin = facesContext.getExternalContext().getRemoteUser();
        user = userRepository.getByEmail(userLogin);
        createMapParams();
        keysData = new ArrayList<String>(params.keySet());
        createMapPwdParams();
        keysPwd = new ArrayList<String>(pwdParams.keySet());
        keys = new ArrayList<String>();
    }

    public void openDialogForData() {
        typeDlg = 0;
        editedParams = new HashMap<>(params);
        keys = keysData;
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('editDlg').show();");
    }
    
    public void openDialogForPwd() {
        typeDlg = 1;
        editedParams = new HashMap<>(pwdParams);
        keys = keysPwd;
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('editDlg').show();");
    }
    
    public void onDlgBtnClick(){
        switch(typeDlg){
            case 0:
                editPersonalData();
                break;
            case 1:
                changePassword();
                break;
        }
    }

    private void editPersonalData() {
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

    private void changePassword() {
        boolean ok_pwd;
        String pwd = DigestUtils.md5Hex(pwdParams.get("Current password"));
        if (pwdParams.get("New password").equals(pwdParams.get("Retype new password"))
                && pwd.equals(user.getEncrPassword())) {
            ok_pwd = true;
            user.setEncrPassword(DigestUtils.md5Hex(pwdParams.get("Current password")));
            userRepository.update(user);
        } else {
            ok_pwd = false;
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.addCallbackParam("ok_pwd", ok_pwd);
    }
}
