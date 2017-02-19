package com.netcracker.smarthome.web.home;

import com.netcracker.smarthome.business.HomeService;
import com.netcracker.smarthome.model.entities.DataType;
import com.netcracker.smarthome.model.entities.HomeParam;
import com.netcracker.smarthome.model.entities.SmartHome;
import com.netcracker.smarthome.web.common.ContextUtils;
import org.primefaces.context.RequestContext;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import java.io.Serializable;
import java.util.List;

@ManagedBean
@SessionScoped
public class HomeSettingsBean implements Serializable {
    @ManagedProperty(value = "#{homeService}")
    private HomeService homeService;
    private HomeParam selectedParam;
    private List<DataType> types;

    @PostConstruct
    private void init() {
        types = homeService.getDataTypes();
        selectedParam = getDefaultParamValue();
    }

    public HomeParam getDefaultParamValue() {
        return new HomeParam("new param", "new param", getHome(), types.get(0));
    }

    public void setHomeService(HomeService homeService) {
        this.homeService = homeService;
    }

    public List<DataType> getTypes() {
        return types;
    }

    public List<HomeParam> getHomeParams() {
        return getHome().getHomeParams();
    }

    public SmartHome getHome() {
        return ((CurrentUserHomesBean) ContextUtils.getBean("currentUserHomesBean")).getCurrentHome();
    }

    public HomeParam getSelectedParam() {
        return selectedParam;
    }

    public void setSelectedParam(HomeParam selectedParam) {
        this.selectedParam = selectedParam;
    }

    public void saveParam(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        try {
            selectedParam = homeService.updateParam(selectedParam);
        } catch (Exception e) {
            ContextUtils.addErrorMessageToContext("Error during saving changes!");
            context.addCallbackParam("correct", false);
            return;
        }
        SmartHome home = getHome();
        getHome().setHomeParams(homeService.getHomeParams(home));
        context.addCallbackParam("correct", true);
    }

    public String deleteParam() {
        homeService.deleteParam(selectedParam);
        SmartHome home = getHome();
        getHome().setHomeParams(homeService.getHomeParams(home));
        return null;
    }
}
