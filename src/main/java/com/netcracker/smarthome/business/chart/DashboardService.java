package com.netcracker.smarthome.business.chart;

import com.netcracker.smarthome.dal.repositories.DashBoardRepository;
import com.netcracker.smarthome.model.entities.Dashboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class DashboardService {

    private final DashBoardRepository dashBoardRepository;

    @Autowired
    public DashboardService(DashBoardRepository dashBoardRepository) {
        this.dashBoardRepository = dashBoardRepository;
    }

    public List<Dashboard> getDashboards(long homeId){
        return dashBoardRepository.getDahboardsByHomeId(homeId);
    }

    public long getWidgetAmount(Dashboard dashboard){
        return dashBoardRepository.getWidgetsAmount(dashboard.getDashboardId());
    }

    @Transactional(readOnly = false)
    public void addDashboard(Dashboard dashboard){
        dashBoardRepository.save(dashboard);
    }

    @Transactional(readOnly = false)
    public void deleteDashboard(Dashboard dashboard){
        dashBoardRepository.delete(dashboard.getDashboardId());
    }

    @Transactional(readOnly = false)
    public void updateDashboard(Dashboard dashboard){
        dashBoardRepository.update(dashboard);
    }

}
