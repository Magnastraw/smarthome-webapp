package com.netcracker.smarthome.dal.repositories;

import com.netcracker.smarthome.model.entities.Group;
import com.netcracker.smarthome.model.entities.GroupPermission;
import com.netcracker.smarthome.model.entities.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.List;

@Repository
public class GroupRepository extends EntityRepository<Group> {
    public GroupRepository() {
        super(Group.class);
    }

    @Transactional
    public List<GroupPermission> getPermissions(Group group) {
        Query query = manager.createQuery("select p from GroupPermission p where p.group.groupId = :groupId");
        query.setParameter("groupId", group.getGroupId());
        return query.getResultList();
    }

    @Transactional
    public List<User> getMembers(Group group) {
        Query query = manager.createQuery("select u from User u join GroupMember gm on u.userId=gm.userId where gm.groupId = :groupId and not gm.admin");
        query.setParameter("groupId", group.getGroupId());
        return query.getResultList();
    }

    @Transactional
    public User getManager(Group group) {
        Query query = manager.createQuery("select u from User u join GroupMember gm on u.userId=gm.userId where gm.groupId = :groupId and gm.admin");
        query.setParameter("groupId", group.getGroupId());
        List<User> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }
}
