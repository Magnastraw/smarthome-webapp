package com.netcracker.smarthome.dal;

import com.netcracker.smarthome.model.entities.SocialProfile;
import com.netcracker.smarthome.model.entities.SocialService;
import com.netcracker.smarthome.model.entities.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestApp {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserRepository repo = context.getBean(UserRepository.class);
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Smith");
        user.setEmail("user@user.com");
        user.setEncrPassword("12345");
        repo.create(user);
        /*user = repo.get((long)0);
        user.setLastName("Brown");
        repo.update(user);
        System.out.println(repo.get((long)0).getLastName());
        repo.delete((long)0);*/
        SocialServiceRepository srepo=context.getBean(SocialServiceRepository.class);
        SocialService service=new SocialService();
        service.setServiceName("facebook");
        service.setClientId("hgjhgjhgj");
        service.setSecretKey("fhghgfjfh");
        srepo.create(service);
        SocialProfileRepository prepo=context.getBean(SocialProfileRepository.class);
        SocialProfile sp=new SocialProfile();
        sp.setService(service);
        sp.setUser(user);
        sp.setUserSocialId("jhjgjhgjhgjhg");
        prepo.create(sp);
    }
}
