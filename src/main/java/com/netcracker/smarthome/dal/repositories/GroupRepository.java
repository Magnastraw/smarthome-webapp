package com.netcracker.smarthome.dal.repositories;

import com.netcracker.smarthome.model.entities.Group;
import com.netcracker.smarthome.model.entities.User;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class GroupRepository extends EntityRepository<Group> {
    public GroupRepository() {
        super(Group.class);
    }

    public List<User> getMembers(Group group) {
        Query query = getManager().createQuery("select u from User u join GroupMember gm on u.userId=gm.userId where gm.groupId = :groupId");
        query.setParameter("groupId", group.getGroupId());
        return query.getResultList();
    }
}
