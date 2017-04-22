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

    public Metric getMetric(long smartHomeId, long objectId, Long subobjectId, long specId) {
        Query query;
        if (subobjectId != null) {
            query = getManager().createQuery("select m from Metric m where (m.smartHome.smartHomeId = :smartHomeId and " +
                    "m.object.smartObjectId = :objectId and " +
                    "m.subobject.smartObjectId = :subobjectId and " +
                    "m.metricSpec.specId = :specId)");
            query.setParameter("smartHomeId", smartHomeId);
            query.setParameter("objectId", objectId);
            query.setParameter("specId", specId);
            query.setParameter("subobjectId", subobjectId);
        }
        else {
            query = getManager().createQuery("select m from Metric m where (m.smartHome.smartHomeId = :smartHomeId and " +
                    "m.object.smartObjectId = :objectId and " +
                    "m.subobject.smartObjectId is null and " +
                    "m.metricSpec.specId = :specId)");
            query.setParameter("smartHomeId", smartHomeId);
            query.setParameter("objectId", objectId);
            query.setParameter("specId", specId);
        }
        List<Metric> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }
}
