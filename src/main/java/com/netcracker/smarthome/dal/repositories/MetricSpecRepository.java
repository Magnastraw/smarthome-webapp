package com.netcracker.smarthome.dal.repositories;

import com.netcracker.smarthome.model.entities.MetricSpec;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class MetricSpecRepository extends EntityRepository<MetricSpec> {
    public MetricSpecRepository() {
        super(MetricSpec.class);
    }

    public List<MetricSpec> getSpecByObjectId(long userId, long objectId) {
        Query query = getManager().createQuery("select ms from MetricSpec ms left join Metric m on ms.specId = m.metricSpec.specId left join SmartHome sh on m.smartHome.smartHomeId=sh.smartHomeId where m.object.smartObjectId = :objectId and sh.user.userId = :userId");
        query.setParameter("userId", userId);
        query.setParameter("objectId", objectId);
        return query.getResultList();
    }
}
