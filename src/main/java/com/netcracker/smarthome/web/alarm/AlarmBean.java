package com.netcracker.smarthome.web.alarm;

import com.netcracker.smarthome.business.services.EventService;
import com.netcracker.smarthome.model.entities.Alarm;
import com.netcracker.smarthome.model.entities.EventHistory;
import org.primefaces.component.calendar.Calendar;
import org.primefaces.component.datatable.DataTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;

@ManagedBean(name="alarmBean")
@SessionScoped
public class AlarmBean implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(AlarmListBean.class);
    private Alarm selectedAlarm;
    private List<EventHistory> events;

    @ManagedProperty(value = "#{eventService}")
    private EventService eventService;

    public void onChange(String id) {
        String selector = "centerForm:eventsTable:"+id;
        //Calendar time = (Calendar)events.getComponent();
        Calendar time = (Calendar) FacesContext.getCurrentInstance().getViewRoot().findComponent(selector);
        if(time.getValue() == null) {
            //events.getNewValue() == null || events.getNewValue().equals("")) {
            DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("centerForm:eventsTable");
            dataTable.resetValue();
        }
    }

    /*public String printSubalarms() {
        int size = selectedAlarm.getSubAlarms().size();
        if (size != 0) {
            String subalarms = "";
            for (int i=0; i<size; i++) {
                subalarms += selectedAlarm.getSubAlarms().get(i).getAlarmName();
                if (i != size-1)
                    subalarms += ", ";
            }
            return subalarms;
        }
        else
            return "";
    }*/

    /*setters and getters*/
    public Alarm getSelectedAlarm() {
        return selectedAlarm;
    }

    public void setSelectedAlarm(Alarm selectedAlarm) {
        this.selectedAlarm = selectedAlarm;
        events = eventService.getEventHistory(this.selectedAlarm.getEvent().getEventId());
    }

    public List<EventHistory> getEvents() {
        return events;
    }

    public void setEvents(List<EventHistory> events) {
        this.events = events;
    }

    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }
}
