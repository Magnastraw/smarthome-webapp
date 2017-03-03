package com.netcracker.smarthome.dao.repositories;

import com.netcracker.smarthome.model.entities.SocialProfile;
import org.springframework.stereotype.Repository;

@Repository
public class SocialProfileRepository extends EntityRepository<SocialProfile> {
    public SocialProfileRepository() {
        super(SocialProfile.class);
    }
}
