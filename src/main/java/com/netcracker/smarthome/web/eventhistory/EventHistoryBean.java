package com.netcracker.smarthome.web.eventhistory;

import com.netcracker.smarthome.dal.repositories.EventHistoryRepository;
import com.netcracker.smarthome.model.entities.EventHistory;
import com.netcracker.smarthome.web.specs.table.Filter;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.ToggleEvent;

@ManagedBean(name = "eventHistoryBean")
@ViewScoped
public class EventHistoryBean {

    @ManagedProperty("#{eventHistoryRepository}")
    private EventHistoryRepository eventHistoryRepository;
    private List<EventHistory> history;
    private List<EventHistory> filteredHistory;

    public EventHistoryRepository getEventHistoryRepository() {
        return eventHistoryRepository;
    }

    public void setEventHistoryRepository(EventHistoryRepository eventHistoryRepository) {
        this.eventHistoryRepository = eventHistoryRepository;
    }

    public List<EventHistory> getHistory() {
        return history;
    }

    public void setHistory(List<EventHistory> history) {
        this.history = history;
    }

    public List<EventHistory> getFilteredHistory() {
        return filteredHistory;
    }

    public void setFilteredHistory(List<EventHistory> filteredHistory) {
        this.filteredHistory = filteredHistory;
    }

    @PostConstruct
    public void init() {
        history = eventHistoryRepository.getAll();
    }

    public void onToggle(ToggleEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, event.getComponent().getId() + " toggled", "Status:" + event.getVisibility().name());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    public boolean filterByDate(Object value, Object filter, Locale locale) {
        return Filter.filterByDate(value, filter);
    }
}
