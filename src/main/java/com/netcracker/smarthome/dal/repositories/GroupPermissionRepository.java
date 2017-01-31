package com.netcracker.smarthome.dal.repositories;

import com.netcracker.smarthome.model.entities.GroupPermission;
import org.springframework.stereotype.Repository;

@Repository
public class GroupPermissionRepository extends EntityRepository<GroupPermission> {
    public GroupPermissionRepository() {
        super(GroupPermission.class);
    }
}
