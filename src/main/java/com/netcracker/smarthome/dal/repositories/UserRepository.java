package com.netcracker.smarthome.dal.repositories;

import com.netcracker.smarthome.model.entities.Group;
import com.netcracker.smarthome.model.entities.SocialProfile;
import com.netcracker.smarthome.model.entities.User;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class UserRepository extends EntityRepository<User> {
    public UserRepository() {
        super(User.class);
    }

    public User getByEmail(String email) {
        Query query = getManager().createQuery("select u from User u where u.email = :email");
        query.setParameter("email", email);
        List<User> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    public User getBySocialId(String socialId, AuthService service) {
        Query query = getManager().createQuery("select u from User u join SocialProfile sp on u.userId=sp.userId join SocialService s on s.serviceId=sp.serviceId where sp.userSocialId=:socialId and s.serviceType=:service");
        query.setParameter("socialId", socialId);
        query.setParameter("service", service);
        List<User> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    public List<SocialProfile> getProfiles(User user) {
        Query query = getManager().createQuery("select sp from SocialProfile sp where sp.userId = :userId");
        query.setParameter("userId", user.getUserId());
        return query.getResultList();
    }

    public List<Group> getGroups(User user) {
        Query query = getManager().createQuery("select g from Group g join GroupMember gm on g.groupId=gm.groupId where gm.userId = :userId");
        query.setParameter("userId", user.getUserId());
        return query.getResultList();
    }
}
