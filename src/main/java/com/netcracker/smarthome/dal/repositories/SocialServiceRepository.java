package com.netcracker.smarthome.dal.repositories;

import com.netcracker.smarthome.model.entities.SocialProfile;
import com.netcracker.smarthome.model.entities.SocialService;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.List;

@Repository
public class SocialServiceRepository extends EntityRepository<SocialService> {
    public SocialServiceRepository() {
        super(SocialService.class);
    }

    @Transactional
    public List<SocialProfile> getProfiles(SocialService service) {
        Query query = manager.createQuery("select sp from SocialProfile sp where sp.serviceId = :serviceId");
        query.setParameter("serviceId", service.getServiceId());
        return query.getResultList();
    }
}
