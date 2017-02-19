package com.netcracker.smarthome.dal.repositories;

import com.netcracker.smarthome.model.entities.SmartObject;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class SmartObjectRepository extends EntityRepository<SmartObject> {
    public SmartObjectRepository() {
        super(SmartObject.class);
    }

    public List<SmartObject> getObjectBySpecId(long userId, long smartHomeId, long specId){
        Query query = getManager().createQuery("select so from SmartObject so inner join Metric m on m.object.smartObjectId = so.smartObjectId inner join SmartHome sh on m.smartHome.smartHomeId=sh.smartHomeId inner join MetricSpec ms on m.metricSpec.specId = ms.specId  where (ms.specId = :specId and sh.user.userId = :userId and so.smartHome.smartHomeId = :smartHomeId) group by so.smartObjectId");
        query.setParameter("userId", userId);
        query.setParameter("smartHomeId", smartHomeId);
        query.setParameter("specId", specId);
        return query.getResultList();
    }
}
