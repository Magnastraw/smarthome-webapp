package com.netcracker.smarthome.business.auth;

public class UserExistsException extends Exception {
    public UserExistsException(String message) {
        super(message);
    }
}
