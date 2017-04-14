package com.netcracker.smarthome.dal.repositories;

import com.netcracker.smarthome.model.entities.SocialService;
import com.netcracker.smarthome.model.enums.AuthService;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class SocialServiceRepository extends EntityRepository<SocialService> {
    public SocialServiceRepository() {
        super(SocialService.class);
    }

    public SocialService getByServiceType(AuthService serviceType) {
        Query query = getManager().createQuery("select s from SocialService s where s.serviceType= :serviceType");
        query.setParameter("serviceType", serviceType);
        List<SocialService> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }
}
