package com.netcracker.smarthome.dal.repositories;

import com.netcracker.smarthome.model.entities.ObjectParam;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class ObjectParamRepository extends EntityRepository<ObjectParam> {
    public ObjectParamRepository() {
        super(ObjectParam.class);
    }

    public ObjectParam getByName(long smartObjectId, String name){
        Query query = getManager().createQuery("select op from ObjectParam op where op.object.smartObjectId = :smartObjectId and lower(op.name) = :name");
        query.setParameter("smartObjectId", smartObjectId);
        query.setParameter("name", name);
        List<ObjectParam> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    public List<ObjectParam> getObjectParams(long smartObjectId) {
        Query query = getManager().createQuery("select op from ObjectParam op where op.object.smartObjectId = :smartObjectId");
        query.setParameter("smartObjectId", smartObjectId);
        return query.getResultList();
    }
}
