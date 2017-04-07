package com.netcracker.smarthome.dal.repositories;

import com.netcracker.smarthome.model.entities.Metric;
import org.springframework.stereotype.Repository;
import javax.persistence.Query;
import java.util.List;

@Repository
public class MetricRepository extends EntityRepository<Metric> {
    public MetricRepository() {
        super(Metric.class);
    }

    public Metric getMetric(long smartHomeId, long smartObjectId, long specId) {
        Query query = getManager().createQuery("select m from Metric m where (m.smartHome.smartHomeId = :smartHomeId and m.object.smartObjectId = :smartObjectId and m.metricSpec.specId = :specId)");
        query.setParameter("smartHomeId", smartHomeId);
        query.setParameter("smartObjectId", smartObjectId);
        query.setParameter("specId", specId);
        List<Metric> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

}
