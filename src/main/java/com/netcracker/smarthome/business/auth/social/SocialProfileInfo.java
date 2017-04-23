package com.netcracker.smarthome.business.auth.social;

import com.netcracker.smarthome.model.enums.AuthService;

public class SocialProfileInfo {
    private AuthService service;
    private String socialId;
    private String email;
    private String firstName;
    private String lastName;

    public AuthService getService() {
        return service;
    }

    public void setService(AuthService service) {
        this.service = service;
    }

    public String getSocialId() {
        return socialId;
    }

    public void setSocialId(String socialId) {
        this.socialId = socialId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
