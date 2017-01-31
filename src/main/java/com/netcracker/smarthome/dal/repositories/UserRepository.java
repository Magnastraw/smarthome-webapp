package com.netcracker.smarthome.dal.repositories;

import com.netcracker.smarthome.model.entities.Group;
import com.netcracker.smarthome.model.entities.SocialProfile;
import com.netcracker.smarthome.model.entities.User;
import com.netcracker.smarthome.model.entities.UserPermission;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.List;

@Repository
public class UserRepository extends EntityRepository<User> {
    public UserRepository() {
        super(User.class);
    }

    @Transactional
    public User getByEmail(String email) {
        Query query = manager.createQuery("select u from User u where u.email = :email");
        query.setParameter("email", email);
        List<User> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    @Transactional
    public List<SocialProfile> getProfiles(User user) {
        Query query = manager.createQuery("select sp from SocialProfile sp where sp.userId = :userId");
        query.setParameter("userId", user.getUserId());
        return query.getResultList();
    }

    @Transactional
    public List<Group> getGroups(User user) {
        Query query = manager.createQuery("select g from Group g join GroupMember gm on g.groupId=gm.groupId where gm.userId = :userId and not gm.admin");
        query.setParameter("userId", user.getUserId());
        return query.getResultList();
    }

    @Transactional
    public List<Group> getManagedGroups(User user) {
        Query query = manager.createQuery("select g from Group g join GroupMember gm on g.groupId=gm.groupId where gm.userId = :userId and gm.admin");
        query.setParameter("userId", user.getUserId());
        return query.getResultList();
    }
}
