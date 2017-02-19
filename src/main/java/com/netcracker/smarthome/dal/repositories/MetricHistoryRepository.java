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
        Query query = getManager().createQuery("select mh from MetricHistory mh left join Metric m on mh.metric.id = m.id where m.object.id = :objectId");
        query.setParameter("objectId", objectId);
        return query.getResultList();
    }


    public List<MetricHistory> getMetricHistoryBySpecId(long specId){
        Query query = getManager().createQuery("select mh from MetricHistory mh left join Metric m on mh.metric.id = m.id where m.metricSpec.specId = :specId");
        query.setParameter("specId", specId);
        return query.getResultList();
    }

    public List<MetricHistory> getMetricHistoryBySpecIdObjectId(long specId, long objectId){
        Query query = getManager().createQuery("select mh from MetricHistory mh left join Metric m on mh.metric.id = m.id where m.metricSpec.specId = :specId and m.object.smartObjectId = :objectId");
        query.setParameter("specId", specId);
        query.setParameter("objectId", objectId);
        return query.getResultList();
    }

    public Metric getMetricById(long id){
        Query query = getManager().createQuery("select m from Metric m where m.id = :id");
        query.setParameter("id", id);
        return (Metric) query.getSingleResult();
    }
}
