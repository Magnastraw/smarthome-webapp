package com.netcracker.smarthome.dal;

import com.netcracker.smarthome.model.entities.SocialService;
import org.springframework.stereotype.Repository;

@Repository
public class SocialServiceRepository extends EntityRepository<SocialService> {
    @Override
    public void setEntityClass() {
        entityClass = SocialService.class;
    }
}
