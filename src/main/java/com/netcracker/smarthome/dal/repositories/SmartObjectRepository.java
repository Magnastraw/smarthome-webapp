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

    public List<SmartObject> getObjectBySpecId(long specId){
        Query query = getManager().createQuery("select so from SmartObject so inner join Metric m on m.object.smartObjectId = so.smartObjectId where m.metricSpec.specId = :specId group by so.smartObjectId");
        query.setParameter("specId", specId);
        return query.getResultList();
    }
}
