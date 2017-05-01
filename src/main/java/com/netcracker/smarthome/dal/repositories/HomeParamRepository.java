package com.netcracker.smarthome.dal.repositories;

import com.netcracker.smarthome.model.entities.HomeParam;
import com.netcracker.smarthome.model.entities.SmartHome;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class HomeParamRepository extends EntityRepository<HomeParam> {
    public HomeParamRepository() {
        super(HomeParam.class);
    }

    @Override
    public List<HomeParam> getAll() {
        Query query = getManager().createQuery("select hp from HomeParam hp order by hp.paramId");
        return query.getResultList();
    }

    public HomeParam getHomeParamByName(long smartHomeId, String paramName) {
        Query query = getManager().createQuery("select hp from HomeParam hp where hp.smartHome.smartHomeId = :smartHomeId and lower(hp.name) = :name");
        query.setParameter("smartHomeId", smartHomeId);
        query.setParameter("name", paramName);
        List<HomeParam> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    public SmartHome getHomeBySecretKey(long secretKey) {
        Query query = getManager().createQuery("select hp.smartHome from HomeParam hp where lower(hp.name) = 'secretkey' and hp.value = :secretKey");
        query.setParameter("secretKey", Long.toString(secretKey));
        List<SmartHome> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }
}
