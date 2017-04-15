package com.netcracker.smarthome.web.home;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netcracker.smarthome.business.HomeService;
import com.netcracker.smarthome.business.endpoints.jsonentities.JsonInventoryObject;
import com.netcracker.smarthome.model.entities.DataType;
import com.netcracker.smarthome.model.entities.HomeParam;
import com.netcracker.smarthome.model.entities.SmartHome;
import com.netcracker.smarthome.web.common.ContextUtils;
import org.json.simple.JSONObject;
import org.primefaces.context.RequestContext;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@ManagedBean
@SessionScoped
public class HomeSettingsBean implements Serializable {
    @ManagedProperty(value = "#{homeService}")
    private HomeService homeService;
    @ManagedProperty(value = "#{currentUserHomesBean}")
    private CurrentUserHomesBean userHomesBean;
    private HomeParam selectedParam;
    private List<DataType> types;

    @PostConstruct
    private void init() {
        types = homeService.getDataTypes();
        selectedParam = getDefaultParamValue();
    }

    public void test() {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("d:\\master\\file.json");
        try {
            objectMapper.writeValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, JsonInventoryObject.class));

            /*FileWriter writer = new FileWriter("d:\\master\\file.json");
            writer.write(json.toJSONString());
            writer.flush();
            writer.close();*/
        } catch (IOException ex) {
            System.err.println(ex);
        }
        /*ParameterMap par = new ParameterMap();
        HashMap<String, JsonParameter> map = new HashMap<String, JsonParameter>();
        map.put("par1", new JsonParameter("param1value", "string"));
        par.setParameterHashMap(map);
        JSONObject json = new JSONObject(map);
        try {
            FileWriter writer = new FileWriter("d:\\master\\file.json");
            writer.write(json.toJSONString());
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            System.err.println(ex);
        }*/
    }

    public HomeParam getDefaultParamValue() {
        return new HomeParam("New param", "New param", getHome(), types.get(0));
    }

    public void setHomeService(HomeService homeService) {
        this.homeService = homeService;
    }

    public void setUserHomesBean(CurrentUserHomesBean userHomesBean) {
        this.userHomesBean = userHomesBean;
    }

    public List<DataType> getTypes() {
        return types;
    }

    public List<HomeParam> getHomeParams() {
        return getHome().getHomeParams();
    }

    public SmartHome getHome() {
        return userHomesBean.getCurrentHome();
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
