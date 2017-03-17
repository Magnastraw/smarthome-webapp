package com.netcracker.smarthome.dal.repositories;

import com.netcracker.smarthome.model.entities.Chart;
import org.springframework.stereotype.Repository;


import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;

@Repository
public class ChartRepository extends EntityRepository<Chart> {
    public ChartRepository() {
        super(Chart.class);
    }

    public long getChartId() {
        Query query = getManager().createNativeQuery("SELECT is_called FROM charts_chart_id_seq");
        if (!(Boolean) query.getResultList().get(0)) {
            return 0;
        } else {
            Query q = getManager().createNativeQuery("SELECT last_value FROM charts_chart_id_seq ");
            BigInteger result = (BigInteger) q.getResultList().get(0);
            return result.longValue();
        }
    }

    public List<Chart> getChartByDashboardId(long dashboardId) {
        Query query = getManager().createQuery("select chart from Chart chart where chart.dashboard.dashboardId = :dashboardId ");
        query.setParameter("dashboardId", dashboardId);
        return query.getResultList();
    }

    public List<Chart> getChartByRenderId(String id) {
        Query query = getManager().createQuery("select chart from Chart chart where :id like CONCAT('%',chart.chartId,'%')");
        query.setParameter("id", id);
        return query.getResultList();
    }


}
