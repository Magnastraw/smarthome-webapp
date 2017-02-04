package com.netcracker.smarthome.web;


import com.netcracker.smarthome.dal.UserDAO;
import com.netcracker.smarthome.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("UserService")
@Transactional(readOnly = true)
public class UserService {

    @Autowired
    UserDAO userDAO;

    /**
     * Add User
     *
     * @param user User
     */
    @Transactional(readOnly = false)
    public void addUser(User user) {
        getUserDAO().addUser(user);
    }

    /**
     * Get Users List
     */

    public List<User> getUsers() {
        return getUserDAO().getUsers();
    }

    /**
     * Get Users DAO
     *
     * @return usersDAO - Users DAO
     */
    public UserDAO getUserDAO() {
        return userDAO;
    }

    /**
     * Set User DAO
     *
     * @param usersDAO - User DAO
     */
    public void setUserDAO(UserDAO usersDAO) {
        this.userDAO = userDAO;
    }

}
