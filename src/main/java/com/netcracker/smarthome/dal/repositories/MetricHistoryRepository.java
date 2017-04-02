package com.netcracker.smarthome.dal.repositories;

import com.netcracker.smarthome.model.entities.Metric;
import com.netcracker.smarthome.model.entities.MetricHistory;
import com.netcracker.smarthome.model.enums.ChartInterval;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

@Repository
public class MetricHistoryRepository extends EntityRepository<MetricHistory> {
    public MetricHistoryRepository() {
        super(MetricHistory.class);
    }

    public List<Object[]> getMetricsHistoryBySpecIdObjectId( long homeId, long specId, long objectId,int rownum,int seriesSize){
        Query query = getManager().createQuery("select mh.historyId,mh.readDate,mh.value ,mh.metric.metricId from MetricHistory mh inner join Metric m on mh.metric.metricId = m.metricId where (m.metricSpec.specId = :specId and m.object.smartObjectId = :objectId and m.smartHome.smartHomeId = :homeId)");
        query.setFirstResult(rownum);
        query.setMaxResults(seriesSize);
        query.setParameter("specId", specId);
        query.setParameter("objectId", objectId);
        query.setParameter("homeId", homeId);

        return (List<Object[]>)query.getResultList();
    }

    public List<MetricHistory> getMetricsHistory( long homeId, long specId, long objectId,Timestamp selectDate){
        Query query = getManager().createQuery("select mh from MetricHistory mh inner join Metric m on mh.metric.metricId = m.metricId where (mh.readDate > :customDate and  m.metricSpec.specId = :specId and m.object.smartObjectId = :objectId and m.smartHome.smartHomeId = :homeId) ");
        query.setMaxResults(1000);
        query.setParameter("customDate",selectDate);
        query.setParameter("specId", specId);
        query.setParameter("objectId", objectId);
        query.setParameter("homeId", homeId);
        return query.getResultList();
    }

    public List<MetricHistory> getLastMetricsHistory( long homeId, long specId, long objectId){
        Query query = getManager().createQuery("select mh from MetricHistory mh inner join Metric m on mh.metric.metricId = m.metricId where (m.metricSpec.specId = :specId and m.object.smartObjectId = :objectId and m.smartHome.smartHomeId = :homeId) ");
        query.setParameter("specId", specId);
        query.setParameter("objectId", objectId);
        query.setParameter("homeId", homeId);
        return  query.setMaxResults(1).getResultList();
    }

    public List<Object[]> getRangedMetricsHistoryBySpecIdObjectId( long homeId, long specId, long objectId,int rownum,int seriesSize){
        Query q = getManager().createNativeQuery("SELECT d.date, AVG(m.value)\n" +
                "        FROM (SELECT to_char(date_trunc('day', (current_date - offs)), 'YYYY-MM-DD') AS date\n" +
                "              FROM generate_series(0, 365, 1) AS offs\n" +
                "        ) d INNER JOIN\n" +
                "        (SELECT metricHist.read_date,metricHist.value FROM metrics_history metricHist inner join metrics metric on metric.metric_id=metricHist.metric_id where (metric.object_id = ?1 and metric.spec_id = ?2 and metric.smart_home_id=?3)) m \n" +
                "        ON d.date = to_char(date_trunc('day', m.read_date), 'YYYY-MM-DD')\n" +
                "        GROUP BY d.date");
        q.setFirstResult(rownum);
        q.setMaxResults(seriesSize);
        q.setParameter(1, objectId);
        q.setParameter(2, specId);
        q.setParameter(3, homeId);
        return (List<Object[]>)q.getResultList();
    }

}
