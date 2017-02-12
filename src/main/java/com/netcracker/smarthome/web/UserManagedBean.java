package com.netcracker.smarthome.web;


import com.netcracker.smarthome.model.User;
import org.primefaces.json.JSONArray;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "userMB")
@RequestScoped
public class UserManagedBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String TABLE = "table";
    private static final String ERROR = "error";

    @ManagedProperty(value = "#{UserService}")
    UserService userService;

    private List<User> usersList;
    private String jsonData;

    private User user = new User();

//    @PostConstruct
//    public void init() {
//        user = new User();
//    }

    /**
     * Add User
     *
     *
     */
    public void addUser() {
            user.setUserId(0);
            getUserService().addUser(user);
    }

    /**
     * Reset Fields
     */
    public void reset() {
        user.setFirstName("");
        user.setLastName("");
        user.setEmail("");
        user.setPhoneNumber("");
        user.setEncrPassword("");
        user.setTwoFactorAuth(false);
    }


    /**
     * Get Users List
     *
     * @return List - Users List
     */
    public List<User> getUsersList() {
        usersList = new ArrayList<User>();
        usersList.addAll(getUserService().getUsers());
        return usersList;
    }

    public String getJsonData() {
        //id from users
        JSONArray usersArray = new JSONArray();
        for(User user :getUserService().getUsers()){
            JSONArray userArray = new JSONArray();
            userArray.put(user.getUserId());
            userArray.put(user.getUserId());
            usersArray.put(userArray);
        }
        System.out.println(usersArray);
        //test data
        String s = "[\n" +
                "[1,12],\n" +
                "[2,5],\n" +
                "[3,18],\n" +
                "[4,13],\n" +
                "[5,7],\n" +
                "[6,4],\n" +
                "[7,9],\n" +
                "[8,10],\n" +
                "[9,15],\n" +
                "[10,22]\n" +
                "]";
        return usersArray.toString();
    }

    /**
     * Get Users Service
     *
     * @return UserService - Users Service
     */
    public UserService getUserService() {
        return userService;
    }


    /**
     * Set User Service
     *
     * @param userService UserService - User Service
     */
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Set User List
     *
     * @param usersList List - User List
     */
    public void setUsersList(List<User> usersList) {
        this.usersList = usersList;
    }

    /**
     * Get User First Name
     *
     * @return String - User First Name
     */
    public String getFirstName() {
        return this.user.getFirstName();
    }

    /**
     * Set User First Name
     *
     * @param firstName String - User First Name
     */
    public void setFirstName(String firstName) {
        this.user.setFirstName(firstName);
    }

    /**
     * Get User Last Name
     *
     * @return String - User Last Name
     */
    public String getLastName() {
        return this.user.getLastName();
    }

    /**
     * Set User Last Name
     *
     * @param lastName String - User Last Name
     */
    public void setLastName(String lastName) {
        this.user.setLastName(lastName);
    }

    /**
     * Get User phone number
     *
     * @return String - phone number
     */
    public String getPhoneNumber() {
        return this.user.getPhoneNumber();
    }

    /**
     * Set User Phone Number
     *
     * @param phoneNumber String - User Phone Number
     */
    public void setPhoneNumber(String phoneNumber) {
        user.setPhoneNumber(phoneNumber);
    }

    /**
     * Get User email
     *
     * @return String - email
     */
    public String getEmail() {
        return this.user.getEmail();
    }

    /**
     * Set User email
     *
     * @param email String - User email
     */
    public void setEmail(String email) {
        this.user.setEmail(email);
    }

    /**
     * Get User encrPassword
     *
     * @return String - password
     */
    public String getEncrPassword() {
        return this.user.getEncrPassword();
    }

    /**
     * Set User password
     *
     * @param encrPassword String - User password
     */
    public void setEncrPassword(String encrPassword) {
        this.user.setEncrPassword(encrPassword);
    }

    /**
     * Get User twoFactorAuth
     *
     * @return boolean - twoFactorAuth
     */
    public boolean isTwoFactorAuth() {
        return this.user.isTwoFactorAuth();
    }

    /**
     * Set User twoFactorAuth
     *
     * @param twoFactorAuth boolean - User twoFactorAuth
     */
    public void setTwoFactorAuth(boolean twoFactorAuth) {
        this.user.setTwoFactorAuth(twoFactorAuth);
    }


}
