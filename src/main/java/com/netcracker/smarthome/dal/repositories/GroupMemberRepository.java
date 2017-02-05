package com.netcracker.smarthome.dal.repositories;

import com.netcracker.smarthome.model.entities.GroupMember;
import org.springframework.stereotype.Repository;

@Repository
public class GroupMemberRepository extends EntityRepository<GroupMember> {
    public GroupMemberRepository() {
        super(GroupMember.class);
    }
}
