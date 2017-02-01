package com.netcracker.smarthome.web;


import com.netcracker.smarthome.dal.UsersDAO;
import com.netcracker.smarthome.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("UserService")
@Transactional(readOnly = true)
public class UsersService {

    @Autowired
    UsersDAO usersDAO;

    /**
     * Add User
     *
     * @param user User
     */
    @Transactional(readOnly = false)
    public void addUser(Users user) {
        getUserDAO().addUser(user);
    }

    /**
     * Get Users List
     */

    public List<Users> getUsers() {
        return getUserDAO().getUsers();
    }

    /**
     * Get Users DAO
     *
     * @return usersDAO - Users DAO
     */
    public UsersDAO getUserDAO() {
        return usersDAO;
    }

    /**
     * Set User DAO
     *
     * @param usersDAO - User DAO
     */
    public void setUserDAO(UsersDAO usersDAO) {
        this.usersDAO = usersDAO;
    }

}
