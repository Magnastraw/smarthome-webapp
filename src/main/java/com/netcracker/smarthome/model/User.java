package com.netcracker.smarthome.model;

import javax.persistence.*;


@Entity
@Table(name = "users", schema = "public", catalog = "smarthome_db")

public class User {

    private long userId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String encrPassword;
    private boolean isTwoFactorAuth;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }


    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    @Column(name = "phone_number")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Column(name = "encr_password")
    public String getEncrPassword() {
        return encrPassword;
    }

    public void setEncrPassword(String encrPassword) {
        this.encrPassword = encrPassword;
    }


    @Column(name = "is_two_factor_auth")
    public boolean isTwoFactorAuth() {
        return isTwoFactorAuth;
    }

    public void setTwoFactorAuth(boolean twoFactorAuth) {
        isTwoFactorAuth = twoFactorAuth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User that = (User) o;

        if (userId != that.userId) return false;
        if (isTwoFactorAuth != that.isTwoFactorAuth) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (phoneNumber != null ? !phoneNumber.equals(that.phoneNumber) : that.phoneNumber != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (encrPassword != null ? !encrPassword.equals(that.encrPassword) : that.encrPassword != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (userId ^ (userId >>> 32));
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (encrPassword != null ? encrPassword.hashCode() : 0);
        result = 31 * result + (isTwoFactorAuth ? 1 : 0);
        return result;
    }

}
