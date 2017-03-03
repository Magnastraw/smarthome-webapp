package com.netcracker.smarthome.dao.repositories;

import com.netcracker.smarthome.model.entities.GroupMember;
import org.springframework.stereotype.Repository;

@Repository
public class GroupMemberRepository extends EntityRepository<GroupMember> {
    public GroupMemberRepository() {
        super(GroupMember.class);
    }
}
