package com.netcracker.smarthome.dal;

import com.netcracker.smarthome.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Add user
     *
     * @param user user
     */
    public void addUser(User user) {
        entityManager.persist(user);
    }

    /**
     * Get user List
     *
     * @return List - user list
     */

    public List<User> getUsers() {
        return entityManager.createQuery("from User ")
                .getResultList();
    }
}
