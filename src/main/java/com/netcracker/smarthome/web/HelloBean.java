package com.netcracker.smarthome.web;

import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;

@Component
@Scope("session")
public class HelloBean {
    public String getWelcomeString() {
        return "Hello, world!";
    }
}