package com.netcracker.smarthome.dal;

import com.netcracker.smarthome.model.Users;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UsersDAO {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Add user
     *
     * @param user user
     */
    public void addUser(Users user){
        entityManager.persist(user);
    }

    /**
     * Get user List
     *
     * @return List - user list
     */

    public List<Users> getUsers() {
        return entityManager.createQuery("from Users ")
                .getResultList();
    }
}
