package com.netcracker.smarthome.dal;

import com.netcracker.smarthome.dal.repositories.EntityRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestApp {
    private static final ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

    public static void main(String[] args) {

    }
}
