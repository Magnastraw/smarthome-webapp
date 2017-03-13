package com.netcracker.smarthome.web.home;

import com.netcracker.smarthome.business.HomeService;
import com.netcracker.smarthome.model.entities.SmartHome;
import com.netcracker.smarthome.web.common.ContextUtils;
import com.netcracker.smarthome.web.specs.AlarmSpecsBean;
import com.netcracker.smarthome.web.specs.MetricSpecsBean;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.util.List;

@ManagedBean
@SessionScoped
public class CurrentUserHomesBean {
    @ManagedProperty(value = "#{homeService}")
    private HomeService homeService;
    private SmartHome currentHome;

    @PostConstruct
    private void init() {
        setCurrentHome(getUserHomes().get(0));
    }

    public SmartHome getCurrentHome() {
        return currentHome;
    }

    public void setCurrentHome(SmartHome currentHome) {
        this.currentHome = currentHome;
    }

    public void setHomeService(HomeService homeService) {
        this.homeService = homeService;
    }

    public List<SmartHome> getUserHomes() {
        return homeService.getHomesList(ContextUtils.getCurrentUser());
    }

    public void setCurrentCatalogs(SmartHome home) {
        setCurrentHome(home);
        ((MetricSpecsBean)ContextUtils.getBean("metricSpecsBean")).initialise();
        ((AlarmSpecsBean)ContextUtils.getBean("alarmSpecsBean")).initialise();
    }
}
