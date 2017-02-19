package com.netcracker.smarthome.web.home;

import com.netcracker.smarthome.business.HomeService;
import com.netcracker.smarthome.model.entities.SmartHome;
import com.netcracker.smarthome.web.common.ContextUtils;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class HomeFormBean {
    @ManagedProperty(value = "#{homeService}")
    private HomeService homeService;
    private SmartHome home;
    private boolean creatingMode;

    public boolean getCreatingMode() {
        return creatingMode;
    }

    public void setCreatingMode(boolean creatingMode) {
        this.creatingMode = creatingMode;
    }

    public void setHomeService(HomeService homeService) {
        this.homeService = homeService;
    }

    public SmartHome getHome() {
        return home;
    }

    public void setHome(SmartHome home) {
        this.home = home;
    }

    public SmartHome getDefaultHomeValue() {
        SmartHome home = new SmartHome();
        home.setUser(ContextUtils.getCurrentUser());
        return home;
    }

    public String save() {
        if (home.getDescription() == null)
            home.setDescription(" ");
        if (creatingMode) {
            homeService.createHome(home);
            ((CurrentUserHomesBean) ContextUtils.getBean("currentUserHomesBean")).setCurrentHome(home);
        } else
            homeService.updateHome(home);
        return null;
    }
}
