package com.netcracker.smarthome.dal.repositories;

import com.netcracker.smarthome.model.entities.ObjectType;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class ObjectTypeRepository extends EntityRepository<ObjectType> {
    public ObjectTypeRepository() {
        super(ObjectType.class);
    }

    public ObjectType getByName(String name){
        Query query = getManager().createQuery("select ot from ObjectType ot where lower(ot.name) = :name");
        query.setParameter("name", name);
        List<ObjectType> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    public List<String> getObjectTypes() {
        Query query = getManager().createQuery("select ot.name from ObjectType ot");
        return query.getResultList();
    }
}
