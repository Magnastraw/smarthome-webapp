package com.netcracker.smarthome.dao.repositories;

import com.netcracker.smarthome.model.entities.SocialProfile;
import com.netcracker.smarthome.model.entities.SocialService;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class SocialServiceRepository extends EntityRepository<SocialService> {
    public SocialServiceRepository() {
        super(SocialService.class);
    }

    public List<SocialProfile> getProfiles(SocialService service) {
        Query query = getManager().createQuery("select sp from SocialProfile sp where sp.serviceId = :serviceId");
        query.setParameter("serviceId", service.getServiceId());
        return query.getResultList();
    }
}
