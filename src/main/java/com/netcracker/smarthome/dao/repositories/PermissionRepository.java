package com.netcracker.smarthome.dao.repositories;

import com.netcracker.smarthome.model.entities.Permission;
import org.springframework.stereotype.Repository;

@Repository
public class PermissionRepository extends EntityRepository<Permission> {
    public PermissionRepository() {
        super(Permission.class);
    }
}
