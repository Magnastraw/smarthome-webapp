package com.netcracker.smarthome.dal.repositories;

import com.netcracker.smarthome.model.entities.SmartObject;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SmartObjectRepository extends EntityRepository<SmartObject> {
    public SmartObjectRepository() {
        super(SmartObject.class);
    }

    public List<SmartObject> getObjectBySpecId(long smartHomeId, long specId){
        Query query = getManager().createQuery("select so from SmartObject so inner join Metric m on m.object.smartObjectId = so.smartObjectId inner join MetricSpec ms on m.metricSpec.specId = ms.specId  where (ms.specId = :specId and m.smartHome.smartHomeId = :smartHomeId) group by so.smartObjectId");
        query.setParameter("smartHomeId", smartHomeId);
        query.setParameter("specId", specId);
        return query.getResultList();
    }

    public List<SmartObject> getObjectByHomeId(long smartHomeId){
        Query query = getManager().createQuery("select smartObject from SmartObject smartObject where (smartObject.smartHome.smartHomeId = :smartHomeId and smartObject.parentObject = null)");
        query.setParameter("smartHomeId", smartHomeId);
        return query.getResultList();
    }

    public List<SmartObject> getSubObjectByObjectIds(ArrayList<Long> objectId){
        Query query = getManager().createQuery("select smartObject from SmartObject smartObject where smartObject.parentObject.smartObjectId in :objectId");
        query.setParameter("objectId",objectId);
        return query.getResultList();
    }

    public List<SmartObject> getObjectsByCatalogId(long smartHomeId, long catalogId){
        Query query = getManager().createQuery("select smartObject from SmartObject smartObject where (smartObject.smartHome.smartHomeId=:smartHomeId and smartObject.catalog.catalogId=:catalogId)");
        query.setParameter("catalogId", catalogId);
        query.setParameter("smartHomeId", smartHomeId);
        return query.getResultList();
    }

}
