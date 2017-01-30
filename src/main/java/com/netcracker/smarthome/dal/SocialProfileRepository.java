package com.netcracker.smarthome.dal;

import com.netcracker.smarthome.model.entities.SocialProfile;
import org.springframework.stereotype.Repository;

@Repository
public class SocialProfileRepository extends EntityRepository<SocialProfile> {
    @Override
    public void setEntityClass() {
        entityClass = SocialProfile.class;
    }
}
