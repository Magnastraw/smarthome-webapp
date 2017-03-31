package com.netcracker.smarthome.dal.repositories;

import com.netcracker.smarthome.model.entities.Metric;
import com.netcracker.smarthome.model.entities.MetricSpec;
import com.netcracker.smarthome.model.entities.Policy;
import com.netcracker.smarthome.model.entities.SmartObject;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class MetricRepository extends EntityRepository<Metric> {
    public MetricRepository() {
        super(Metric.class);
    }

    public Double getLastMetricValue(SmartObject object, MetricSpec spec) {
        Query query = getManager().createQuery("select max(mh.value) from Metric m join m.metricHistory mh on m.metricSpec=:spec and m.subobject=:object");
        query.setParameter("spec", spec);
        query.setParameter("object", object);
        Object result = query.getSingleResult();
        return (Double) result;
    }

    public Double getLastMetricValue(Policy policy, MetricSpec spec) {
        Query query = getManager().createQuery("select a.object from Policy p join p.assignments a where p=:policy");
        query.setParameter("policy", policy);
        List<SmartObject> assignedObjects = query.getResultList();
        return (Double) getLastMetricValue(assignedObjects, spec);
    }

    private Double getLastMetricValue(List<SmartObject> objects, MetricSpec spec) {
        Query query = getManager().createQuery("select max(mh.value) from Metric m join m.metricHistory mh on m.metricSpec=:spec and m.subobject in :objects");
        query.setParameter("spec", spec);
        query.setParameter("objects", objects);
        Object result = query.getSingleResult();
        return (Double) result;
    }
}
