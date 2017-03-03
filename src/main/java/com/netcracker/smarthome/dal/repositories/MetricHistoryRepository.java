package com.netcracker.smarthome.dal.repositories;

import com.netcracker.smarthome.model.entities.Metric;
import com.netcracker.smarthome.model.entities.MetricHistory;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;

@Repository
public class MetricHistoryRepository extends EntityRepository<MetricHistory> {
    public MetricHistoryRepository() {
        super(MetricHistory.class);
    }

    public List<MetricHistory> getMetricsHistory(){
        Query query = getManager().createQuery("select mh from MetricHistory mh ");
        return query.getResultList();
    }

    public List<MetricHistory> getMetricHistoryByObjectId(long objectId){
        Query query = getManager().createQuery("select mh from MetricHistory mh left join Metric m on mh.metric.metricId = m.metricId where m.object.smartObjectId = :objectId");
        query.setParameter("objectId", objectId);
        return query.getResultList();
    }


    public List<MetricHistory> getMetricHistoryBySpecId(long specId){
        Query query = getManager().createQuery("select mh from MetricHistory mh left join Metric m on mh.metric.metricId = m.metricId where m.metricSpec.specId = :specId");
        query.setParameter("specId", specId);
        return query.getResultList();
    }

    public List<Object[]> getMetricsHistoryBySpecIdObjectId( long homeId, long specId, long objectId){
        Query query = getManager().createQuery("select mh.historyId,mh.readDate,mh.value ,mh.metric.metricId from MetricHistory mh inner join Metric m on mh.metric.metricId = m.metricId inner join SmartObject sm on m.object.smartObjectId = sm.smartObjectId where (m.metricSpec.specId = :specId and sm.smartObjectId = :objectId and m.smartHome.smartHomeId = :homeId)");
        query.setFirstResult(1);
        query.setMaxResults(500);
        query.setParameter("specId", specId);
        query.setParameter("objectId", objectId);
        query.setParameter("homeId", homeId);
        return (List<Object[]>)query.getResultList();
    }

    public Object[] getMetricHistoryBySpecIdObjectId( long userId,long homeId, long specId, long objectId,int rownum ){
        Query query = getManager().createQuery("select mh.historyId,mh.readDate,mh.value ,mh.metric.metricId from MetricHistory mh inner join Metric m on mh.metric.metricId = m.metricId inner join SmartHome sh on m.smartHome.smartHomeId = sh.smartHomeId inner join SmartObject sm on m.object.smartObjectId = sm.smartObjectId where (m.metricSpec.specId = :specId and sm.smartObjectId = :objectId and sh.user.userId = :userId and m.smartHome.smartHomeId = :homeId) order by mh.readDate");
        query.setFirstResult(rownum);
        query.setMaxResults(1);
        query.setParameter("specId", specId);
        query.setParameter("objectId", objectId);
        query.setParameter("userId", userId);
        query.setParameter("homeId", homeId);
        List<Object[]> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    public Metric getMetricById(long id){
        Query query = getManager().createQuery("select m from Metric m where m.metricId = :id");
        query.setParameter("id", id);
        return (Metric) query.getSingleResult();
    }

    public List<Object[]> getMetricsHistoryBySpecId(long smartHomeId, long metricSpecId){
        Query query = getManager().createQuery("select metricHistory from MetricHistory metricHistory inner join Metric metric on metricHistory.metric.metrricId=metric.metricId where (metric.metricSpec.specId= :metricSpecId and metric.smartHome.smartHomeId = :smartHomeId)");
        query.setParameter("smartHomeId",smartHomeId);
        query.setParameter("metricSpecId",metricSpecId);
        return (List<Object[]>)query.getResultList();
    }
}
