package com.netcracker.smarthome.web;


import com.netcracker.smarthome.model.Users;
import org.springframework.dao.DataAccessException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "userMB")
@RequestScoped
public class UsersManagedBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String TABLE = "table";
    private static final String ERROR = "error";

    @ManagedProperty(value = "#{UserService}")
    UsersService usersService;

    List<Users> usersList;

    private int user_id;
    private String first_name;
    private String last_name;
    private String phone_number;
    private String email;
    private String encr_password;
    private boolean is_two_factor_auth;

    /**
     * Add User
     *
     * @return String - Response Message
     */
    public String addUser() {

        try {
            Users user = new Users();
            user.setUserId(0);
            user.setFirstName(getFirst_name());
            user.setLastName(getLast_name());
            user.setPhoneNumber(getPhone_number());
            user.setEmail(getEmail());
            user.setEncrPassword(getEncr_password());
            user.setTwoFactorAuth(getIs_two_factor_auth());
            getUsersService().addUser(user);///////////////////////////////
            return TABLE;
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return ERROR;
    }

    /**
     * Reset Fields
     */
    public void reset() {
        this.setFirst_name("");
        this.setLast_name("");
        this.setEmail("");
        this.setPhone_number("");
        this.setEncr_password("");
        this.setEncr_password("");
    }


    /**
     * Get Users List
     *
     * @return List - Users List
     */
    public List<Users> getUserList() {
        usersList = new ArrayList<Users>();
        usersList.addAll(getUsersService().getUsers());
        return usersList;
    }

    /**
     * Get Users Service
     *
     * @return UserService - Users Service
     */
    public UsersService getUsersService() {
        return usersService;
    }


    /**
     * Set User Service
     *
     * @param usersService UserService - User Service
     */
    public void setUsersService(UsersService usersService) {
        this.usersService = usersService;
    }

    /**
     * Set User List
     *
     * @param usersList List - User List
     */
    public void setUsersList(List<Users> usersList) {
        this.usersList = usersList;
    }

    /**
     * Get User First Name
     *
     * @return String - User First Name
     */
    public String getFirst_name() {
        return first_name;
    }

    /**
     * Set User First Name
     *
     * @param first_name String - User First Name
     */
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    /**
     * Get User Last Name
     *
     * @return String - User Last Name
     */
    public String getLast_name() {
        return last_name;
    }

    /**
     * Set User Last Name
     *
     * @param last_name String - User Last Name
     */
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    /**
     * Get User phone number
     *
     * @return String - phone number
     */
    public String getPhone_number() {
        return phone_number;
    }

    /**
     * Set User Phone Number
     *
     * @param phone_number String - User Phone Number
     */
    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    /**
     * Get User email
     *
     * @return String - email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set User email
     *
     * @param email String - User email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get User encr_password
     *
     * @return String - password
     */
    public String getEncr_password() {
        return encr_password;
    }

    /**
     * Set User password
     *
     * @param encr_password String - User password
     */
    public void setEncr_password(String encr_password) {
        this.encr_password = encr_password;
    }

    /**
     * Get User two_factor_auth
     *
     * @return boolean - two_factor_auth
     */
    public boolean getIs_two_factor_auth() {
        return is_two_factor_auth;
    }

    /**
     * Set User two_factor_auth
     *
     * @param is_two_factor_auth boolean - User is_two_factor_auth
     */
    public void setIs_two_factor_auth(boolean is_two_factor_auth) {
        this.is_two_factor_auth = is_two_factor_auth;
    }
}
