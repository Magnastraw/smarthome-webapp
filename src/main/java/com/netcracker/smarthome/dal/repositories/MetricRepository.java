package com.netcracker.smarthome.dal.repositories;

import com.netcracker.smarthome.model.entities.Metric;
import com.netcracker.smarthome.model.entities.MetricSpec;
import com.netcracker.smarthome.model.entities.SmartObject;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.sql.Timestamp;
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
        } else {
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

    public Metric get(SmartObject object, SmartObject subobject, MetricSpec spec, Timestamp regDate) {
        Query query = getManager().createQuery("select mh.metric from MetricHistory mh where mh.readDate=:regDate and mh.metric.object=:object and mh.metric.subobject=:subobject and mh.metric.metricSpec=:spec");
        query.setParameter("regDate", regDate);
        query.setParameter("object", object);
        query.setParameter("subobject", subobject);
        query.setParameter("spec", spec);
        List<Metric> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    public Double getLastMetricValueByObject(long objectId, long specId) {
        Query query = getManager().createQuery("select max(mh.value) from Metric m join m.metricHistory mh on m.metricSpec.specId=:spec and (m.subobject.smartObjectId=:object or m.object.smartObjectId=:object)");
        query.setParameter("spec", specId);
        query.setParameter("object", objectId);
        Object result = query.getSingleResult();
        return (Double) result;
    }

    public Double getLastMetricValueByPolicy(long policyId, long specId) {
        Query query = getManager().createQuery("select obj from Policy p join p.assignedObjects obj where p.policyId=:policy");
        query.setParameter("policy", policyId);
        List<SmartObject> assignedObjects = query.getResultList();
        return getLastMetricValue(assignedObjects, specId);
    }

    private Double getLastMetricValue(List<SmartObject> objects, long spec) {
        Query query = getManager().createQuery("select max(mh.value) from Metric m join m.metricHistory mh on m.metricSpec.specId=:spec and (m.subobject in :objects or m.object in :objects)");
        query.setParameter("spec", spec);
        query.setParameter("objects", objects);
        Object result = query.getSingleResult();
        return (Double) result;
    }
}