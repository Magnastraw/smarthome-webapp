package com.netcracker.smarthome.web.notification;

import com.netcracker.smarthome.dal.repositories.NotificationRepository;
import com.netcracker.smarthome.web.home.CurrentUserHomesBean;
import com.netcracker.smarthome.web.specs.table.Filter;
import org.primefaces.component.calendar.Calendar;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.ToggleEvent;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.List;
import java.util.Locale;

@ManagedBean(name = "notificationBean")
@ViewScoped
public class NotificationBean {

    @ManagedProperty("#{notificationRepository}")
    private NotificationRepository notificationRepository;

    @ManagedProperty("#{currentUserHomesBean}")
    private CurrentUserHomesBean userHomesBean;

    private List<NotificationForView> notifications;
    private List<NotificationForView> filteredNotifications;

    public NotificationRepository getNotificationRepository() {
        return notificationRepository;
    }

    public void setNotificationRepository(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public List<NotificationForView> getNotifications() {
        return notifications;
    }

    public void setNotificationRepository(List<NotificationForView> notifications) {
        this.notifications = notifications;
    }

    public List<NotificationForView> getFilteredNotifications() {
        return filteredNotifications;
    }

    public void setFilteredNotifications(List<NotificationForView> filteredNotifications) {
        this.filteredNotifications = filteredNotifications;
    }

    public List<String> getChannels() {
        return notificationRepository.getChannels();
    }

    public List<String> getStatuses() {
        return notificationRepository.getStatuses();
    }

    public boolean filterByDate(Object value, Object filter, Locale locale) {
        return Filter.filterByDate(value, filter);
    }

    public void onChange(String id) {
        String selector = "centerForm:notificationsDT:" + id;
        Calendar time = (Calendar) FacesContext.getCurrentInstance().getViewRoot().findComponent(selector);
        if (time.getValue() == null) {
            DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("centerForm:notificationsDT");
            dataTable.resetValue();
        }
    }

    @PostConstruct
    public void init() {
        notifications = notificationRepository.getNotifications(userHomesBean.getCurrentHome());
    }

    public void onToggle(ToggleEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, event.getComponent().getId() + " toggled", "Status:" + event.getVisibility().name());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void setUserHomesBean(CurrentUserHomesBean userHomesBean) {
        this.userHomesBean = userHomesBean;
    }
}
