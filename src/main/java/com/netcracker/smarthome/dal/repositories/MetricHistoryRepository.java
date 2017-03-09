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

    public List<Object[]> getMetricsHistoryBySpecIdObjectId( long homeId, long specId, long objectId,int rownum,int seriesSize){
        Query query = getManager().createQuery("select mh.historyId,mh.readDate,mh.value ,mh.metric.metricId from MetricHistory mh inner join Metric m on mh.metric.metricId = m.metricId inner join SmartObject sm on m.object.smartObjectId = sm.smartObjectId where (m.metricSpec.specId = :specId and sm.smartObjectId = :objectId and m.smartHome.smartHomeId = :homeId)");
        query.setFirstResult(rownum);
        query.setMaxResults(seriesSize);
        query.setParameter("specId", specId);
        query.setParameter("objectId", objectId);
        query.setParameter("homeId", homeId);
        return (List<Object[]>)query.getResultList();
    }

}
