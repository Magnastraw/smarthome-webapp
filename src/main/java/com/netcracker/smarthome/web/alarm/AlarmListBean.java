package com.netcracker.smarthome.web.alarm;

import com.netcracker.smarthome.business.services.AlarmService;
import com.netcracker.smarthome.model.entities.Alarm;
import com.netcracker.smarthome.model.entities.SmartHome;
import com.netcracker.smarthome.model.entities.SmartObject;
import com.netcracker.smarthome.model.enums.AlarmSeverity;
import com.netcracker.smarthome.web.home.CurrentUserHomesBean;
import com.netcracker.smarthome.web.specs.table.Filter;
import org.primefaces.component.calendar.Calendar;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.data.FilterEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.*;

@ManagedBean(name="alarmListBean")
@ViewScoped
public class AlarmListBean implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(AlarmListBean.class);
    private List<Alarm> currentAlarms;
    private List<Alarm> alarmPath;
    private List<Alarm> filteredAlarms;
    private List<AlarmSeverity> severities;
    private List<Alarm> selectedAlarms;
    private SmartObject currentObject;

    @ManagedProperty(value = "#{alarmService}")
    private AlarmService alarmService;
    @ManagedProperty(value = "#{currentUserHomesBean}")
    private CurrentUserHomesBean userHomesBean;

    @PostConstruct
    public void init() {
        changeCurrentHome();
    }

    public void changeCurrentHome() {
        currentObject = null;
        alarmPath = new ArrayList<Alarm>();
        getRootAlarms();
        severities = Arrays.asList(AlarmSeverity.values());
    }

    private SmartHome getHome() {
        return userHomesBean.getCurrentHome();
    }

    public void getRootAlarms() {
        if (currentObject != null)
            currentAlarms = alarmService.getRootAlarmsByObject(currentObject.getSmartObjectId());
        else
            currentAlarms = alarmService.getRootAlarms(getHome().getSmartHomeId());
        clearAlarmPath();
        setSubAlarms();
    }

    private void setSubAlarms() {
        for (int i=0; i<currentAlarms.size(); i++) {
            Alarm alarm = currentAlarms.get(i);
            alarm.setSubAlarms(alarmService.getChildrenAlarms(alarm));
        }
    }

    public void expand(Alarm alarm) {
        if(alarm != null) {
            clearAlarmPath();
            alarmPath = alarmService.getPathToAlarm(alarm);
            currentAlarms = alarm.getSubAlarms();
            setSubAlarms();
        }
        else
            getRootAlarms();
    }

    public void onSelect(Alarm alarm) {
        if (alarm.getSubAlarms() != null) {
            expand(alarm);
        }
    }

    public String setStyleClass(int severity) {
        switch (severity) {
            case 2:
                return "severity-info";
            case 3:
                return "severity-warn";
            case 4:
                return "severity-major";
            case 5:
                return "severity-critical";
            default:
                return "severity-normal";
        }
    }

    public void setClearSeverity() {
        if (selectedAlarms != null) {
            LOG.info("Send selected alarms to policy engine" + selectedAlarms);
        }
    }

    public boolean filterByDate(Object value, Object filter, Locale locale) {
        return Filter.filterByDate(value, filter);
    }

    public void onChange(String id) {
        String selector = "centerForm:alarmsDT:"+id;
        //Calendar time = (Calendar)events.getComponent();
        Calendar time = (Calendar) FacesContext.getCurrentInstance().getViewRoot().findComponent(selector);
        if(time.getValue() == null) {
            //events.getNewValue() == null || events.getNewValue().equals("")) {
            DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("centerForm:alarmsDT");
            dataTable.resetValue();
        }
    }

    /*public void onChange() {
        Calendar startTime = (Calendar) FacesContext.getCurrentInstance().getViewRoot().findComponent("centerForm:alarmsDT:startTimeFilter");
        if(startTime.getValue() == null) {
        //events.getNewValue() == null || events.getNewValue().equals("")) {
            DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("centerForm:alarmsDT");
            dataTable.resetValue();
            dataTable.reset();
        }
    }*/

    public void showSelectedObjectAlarms(SmartObject object) {
        setCurrentObject(object);
        getRootAlarms();
    }

    public void onFilter(FilterEvent event) {
        if (((DataTable) event.getComponent()).getFilters().size() == 0) {
            ((DataTable) event.getComponent()).reset();
            ((DataTable) event.getComponent()).resetValue();
            ((DataTable) event.getComponent()).clearLazyCache();
            event.getComponent().clearInitialState();
            /*if (alarmPath.size() != 0)
                expand(alarmPath.get(alarmPath.size()-1));
            else
                getRootAlarms();*/
            FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(event.getComponent().getClientId());

            //RequestContext.getCurrentInstance().update(":centerForm:alarmsDT");
        }
    }

    public void clearAlarmPath() {
        alarmPath.clear();
    }

    /*setters and getters*/
    public void setAlarmService(AlarmService alarmService) {
        this.alarmService = alarmService;
    }

    public void setUserHomesBean(CurrentUserHomesBean userHomesBean) {
        this.userHomesBean = userHomesBean;
    }

    public List<Alarm> getCurrentAlarms() {
        return currentAlarms;
    }

    public void setCurrentAlarms(List<Alarm> currentAlarms) {
        this.currentAlarms = currentAlarms;
    }

    public List<Alarm> getAlarmPath() {
        return alarmPath;
    }

    public void setAlarmPath(List<Alarm> alarmPath) {
        this.alarmPath = alarmPath;
    }

    public List<Alarm> getFilteredAlarms() {
        return filteredAlarms;
    }

    public void setFilteredAlarms(List<Alarm> filteredAlarms) {
        this.filteredAlarms = filteredAlarms;
    }

    /*public List<JsonAlarm> getFilteredAlarms() {
        if (filteredAlarms == null) {
            filteredAlarms = (List<JsonAlarm>)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("filteredData");
        }
        return filteredAlarms;
    }

    public void setFilteredAlarms(List<JsonAlarm> filteredTableEntities) {
        this.filteredAlarms = filteredTableEntities;
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("filteredData", filteredTableEntities);
    }*/

    public List<AlarmSeverity> getSeverities() {
        return severities;
    }

    public void setSeverities(List<AlarmSeverity> severities) {
        this.severities = severities;
    }

    public List<Alarm> getSelectedAlarms() {
        return selectedAlarms;
    }

    public void setSelectedAlarms(List<Alarm> selectedAlarms) {
        this.selectedAlarms = selectedAlarms;
    }

    public SmartObject getCurrentObject() {
        return currentObject;
    }

    public void setCurrentObject(SmartObject currentObject) {
        this.currentObject = currentObject;
    }
}
