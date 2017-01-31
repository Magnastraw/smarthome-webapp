package com.netcracker.smarthome.dal.repositories;

import com.netcracker.smarthome.model.entities.UserPermission;
import org.springframework.stereotype.Repository;

@Repository
public class UserPermissionRepository extends EntityRepository<UserPermission> {
    public UserPermissionRepository() {
        super(UserPermission.class);
    }
}
