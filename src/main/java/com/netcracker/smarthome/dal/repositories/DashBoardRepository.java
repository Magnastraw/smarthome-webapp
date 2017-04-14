package com.netcracker.smarthome.dal.repositories;

import com.netcracker.smarthome.model.entities.Dashboard;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;

@Repository
public class DashBoardRepository extends EntityRepository<Dashboard> {
    public DashBoardRepository() {
        super(Dashboard.class);
    }

    public List<Dashboard> getDahboardsByHomeId(long homeId){
        Query query = getManager().createQuery("select dashboard from Dashboard dashboard where dashboard.smartHome.smartHomeId = :homeId");
        query.setParameter("homeId", homeId);
        return query.getResultList();
    }

    public Long getWidgetsAmount(long dashboardId){
        Query query = getManager().createQuery("select count(*) from Chart chart where chart.dashboard.dashboardId = :dashboardId group by chart.dashboard.dashboardId");
        query.setParameter("dashboardId",dashboardId);
        if(query.getResultList().size()==0){
            return 0L;
        } else{
            return (Long) query.getResultList().get(0);
        }
    }
}
