package com.netcracker.smarthome.web.chart;

import com.netcracker.smarthome.business.chart.ChartService;
import com.netcracker.smarthome.model.entities.Chart;
import com.netcracker.smarthome.model.entities.Dashboard;
import com.netcracker.smarthome.model.entities.SmartHome;
import com.netcracker.smarthome.web.NavigationBean;
import com.netcracker.smarthome.web.common.ContextUtils;
import com.netcracker.smarthome.web.home.CurrentUserHomesBean;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.application.ViewHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ManagedBean(name = "dashboardBean")
@SessionScoped
public class DashboardBean {
    private static final Logger LOG = LoggerFactory.getLogger(DashboardBean.class);

    @ManagedProperty(value = "#{ChartService}")
    ChartService chartService;

    @ManagedProperty(value = "#{navigationBean}")
    NavigationBean navigationBean;

    private List<Dashboard> dashboards;
    private Dashboard dashboard;
    private String dashboardName;
    private Dashboard selectedDashboard;
    private Dashboard currentDashboard;
    private boolean isFromDashboard;
    private boolean update;

    @PostConstruct
    public void init() {
        dashboards = chartService.getDashboards(getCurrentHome().getSmartHomeId());
    }

    public boolean isFromDashboard() {
        return isFromDashboard;
    }

    public void setFromDashboard(boolean fromDashboard) {
        isFromDashboard = fromDashboard;
    }

    public String getDashboardName() {
        return dashboardName;
    }

    public void setDashboardName(String dashboardName) {
        this.dashboardName = dashboardName;
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdateDialog(boolean update,Dashboard dashboard) {
        this.update = update;
        this.currentDashboard = dashboard;
        setDashboardName(currentDashboard.getDashboardName());
    }

    public void setSaveDialog(boolean update){
        this.update = update;
        setDashboardName("");
    }


    public NavigationBean getNavigationBean() {
        return navigationBean;
    }

    public void setNavigationBean(NavigationBean navigationBean) {
        this.navigationBean = navigationBean;
    }

    public Dashboard getDashboard() {
        return dashboard;
    }

    public void setDashboard(Dashboard dashboard) {
        this.dashboard = dashboard;
    }

    public Dashboard getSelectedDashboard() {
        return selectedDashboard;
    }

    public void setSelectedDashboard(Dashboard selectedDashboard) {
        this.selectedDashboard = selectedDashboard;
    }

    public void saveOrUpdate() {
        if (update) {
            for (int i = 0; i < dashboards.size(); i++) {
                if (dashboards.get(i).equals(currentDashboard)) {
                    currentDashboard.setDashboardName(dashboardName);
                    dashboards.set(i, currentDashboard);
                    chartService.updateDashboard(currentDashboard);
                }
            }
        } else {
            Dashboard dashboard = new Dashboard(getCurrentHome(), dashboardName);
            chartService.addDashboard(dashboard);
            dashboards.add(dashboard);
            dashboardName = "";
        }
    }


    private SmartHome getCurrentHome() {
        return ((CurrentUserHomesBean) ContextUtils.getBean("currentUserHomesBean")).getCurrentHome();
    }

    public Dashboard getDefaultDashboard() {
        return new Dashboard(getCurrentHome());
    }

    public void setDashboards(List<Dashboard> dashboards) {
        this.dashboards = dashboards;
    }

    public List<Dashboard> getDashboards() {
        return this.dashboards;
    }

    public Dashboard getCurrentDashboard() {
        return currentDashboard;
    }

    public void setCurrentDashboard(Dashboard currentDashboard) {
        this.currentDashboard = currentDashboard;
        ((ChartBean) ContextUtils.getBean("chartBean")).setCharts(currentDashboard);
    }

    public void setCurrentDashboardFromMenu(Dashboard dashboardFromMenu) {
        this.currentDashboard = dashboardFromMenu;
        if (((ChartBean) ContextUtils.getBean("chartBean")).setCharts(currentDashboard)) {
            navigationBean.setPageName("charts/chartPage");
        }
    }

    public ChartService getChartService() {
        return chartService;
    }

    public void setChartService(ChartService chartService) {
        this.chartService = chartService;
    }

    public void homeChangeListener(SmartHome smartHome) {
        List<Dashboard> dashboardList = chartService.getDashboards(smartHome.getSmartHomeId());
        if (dashboardList.size() > 0) {
            dashboards = dashboardList;
            this.currentDashboard = dashboardList.get(0);
            setCurrentDashboard(this.currentDashboard);
        } else {
            this.dashboard = getDefaultDashboard();
            this.dashboard.setDashboardName("Default dashboard");
            this.dashboards = new ArrayList<Dashboard>();
            this.dashboards.add(dashboard);
            chartService.addDashboard(this.dashboard);
        }

    }

    public void onRowSelect(SelectEvent event) {
        setFromDashboard(true);
        setCurrentDashboard(selectedDashboard);
        navigationBean.setPageName("charts/chartPage");
        selectedDashboard = null;
    }

    public void deleteDashboard(Dashboard dashboard) {
        chartService.deleteDashboard(dashboard);
        dashboards.remove(dashboard);
    }

    public long getWidgetsAmount(Dashboard dashboard) {
        if (dashboard == null) {
            return 0;
        } else {
            return chartService.getWidgetAmount(dashboard);
        }
    }

}
