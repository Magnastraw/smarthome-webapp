package com.netcracker.smarthome.web.chart;

import com.netcracker.smarthome.business.services.DashboardService;
import com.netcracker.smarthome.model.entities.Dashboard;
import com.netcracker.smarthome.model.entities.SmartHome;
import com.netcracker.smarthome.web.NavigationBean;
import com.netcracker.smarthome.web.common.ContextUtils;
import com.netcracker.smarthome.web.home.CurrentUserHomesBean;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "dashboardBean")
@SessionScoped
public class DashboardBean {
    private static final Logger LOG = LoggerFactory.getLogger(DashboardBean.class);

    @ManagedProperty(value = "#{dashboardService}")
    private DashboardService dashboardService;
    @ManagedProperty(value = "#{navigationBean}")
    private NavigationBean navigationBean;

    private List<Dashboard> dashboards;
    private List<Dashboard> dashboardMenuList;
    private Dashboard dashboard;
    private String dashboardName;
    private Dashboard selectedDashboard;
    private Dashboard currentDashboard;
    private boolean isFromDashboard;
    private boolean update;
    private boolean isFromExternal;

    @PostConstruct
    public void init() {
        dashboards = dashboardService.getDashboards(getCurrentHome().getSmartHomeId());
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

    public void setUpdateDialog(boolean update, Dashboard dashboard) {
        this.update = update;
        this.currentDashboard = dashboard;
        setDashboardName(currentDashboard.getDashboardName());
    }

    public void setSaveDialog(boolean update) {
        this.update = update;
        setDashboardName("");
    }

    public List<Dashboard> getDashboardMenuList() {
        return dashboardService.getDashboards(getCurrentHome().getSmartHomeId());
    }

    public void setDashboardMenuList(List<Dashboard> dashboardMenuList) {
        this.dashboardMenuList = dashboardMenuList;
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
                    dashboardService.updateDashboard(currentDashboard);
                }
            }
        } else {
            Dashboard dashboard = new Dashboard(getCurrentHome(), dashboardName);
            dashboardService.addDashboard(dashboard);
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
            navigationBean.setPageName("charts/dashboard");
        }
    }

    public DashboardService getDashboardService() {
        return dashboardService;
    }

    public void setDashboardService(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    public void homeChangeListener(SmartHome smartHome) {
        List<Dashboard> dashboardList = dashboardService.getDashboards(smartHome.getSmartHomeId());
        if (dashboardList.size() > 0) {
            dashboards = dashboardList;
            this.currentDashboard = dashboardList.get(0);
            setCurrentDashboard(this.currentDashboard);
        } else {
            this.dashboard = getDefaultDashboard();
            this.dashboard.setDashboardName("Default dashboard");
            this.dashboards = new ArrayList<Dashboard>();
            this.dashboards.add(dashboard);
            dashboardService.addDashboard(this.dashboard);
            setCurrentDashboard(this.dashboard);
        }
        ((ChartBean)ContextUtils.getBean("chartBean")).init();

    }

    public void onRowSelect(SelectEvent event) {
        setFromDashboard(true);
        setCurrentDashboard(selectedDashboard);
        navigationBean.setPageName("charts/dashboard");
        selectedDashboard = null;
    }

    public void deleteDashboard(Dashboard dashboard) {
        dashboardService.deleteDashboard(dashboard);
        dashboards.remove(dashboard);
    }

    public long getWidgetsAmount(Dashboard dashboard) {
        if (dashboard == null) {
            return 0;
        } else {
            return dashboardService.getWidgetAmount(dashboard);
        }
    }

    public boolean isFromExternal() {
        return isFromExternal;
    }

    public void setFromExternal(boolean fromExternal) {
        isFromExternal = fromExternal;
    }
}
