package com.netcracker.smarthome.business.auth.social;

public class UnsuccessfulRequestException extends Exception {
    public UnsuccessfulRequestException(String message, int statusCode, String reasonPhrase) {
        super(String.format("%s Status is %d %s", message, statusCode, reasonPhrase));
    }
}
