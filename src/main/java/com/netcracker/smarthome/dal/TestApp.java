package com.netcracker.smarthome.dal;

import com.netcracker.smarthome.dal.repositories.SocialProfileRepository;
import com.netcracker.smarthome.dal.repositories.SocialServiceRepository;
import com.netcracker.smarthome.dal.repositories.UserRepository;
import com.netcracker.smarthome.model.entities.SocialProfile;
import com.netcracker.smarthome.model.entities.SocialService;
import com.netcracker.smarthome.model.entities.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class TestApp {
    private static final ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

    public static void main(String[] args) {
        testUsers();
        testSocialProfiles();
    }

    private static <T> void printList(List<T> list) {
        if (list.isEmpty())
            System.out.println("\tEmpty list");
        else {
            for (T obj : list
                    ) {
                System.out.println(obj);
            }
        }
    }

    private static void testUsers() {
        UserRepository repo = context.getBean(UserRepository.class);
        System.out.println("\nTesting users\n**************************************");
        System.out.println("Create new users");
        repo.save(new User("john@email.com", "12345", "John", "Brown", null, false));
        repo.save(new User("vasya@email.com", "12345", "Vasya", "Pupkin", null, false));
        printList(repo.getAll());
        System.out.println("\nAfter updating user");
        User user = repo.getByEmail("john@email.com");
        user.setLastName("Smith");
        repo.update(user);
        printList(repo.getAll());
        System.out.println("\nAfter deleting the first user");
        user = repo.getByEmail("john@email.com");
        repo.delete(user.getUserId());
        printList(repo.getAll());
        System.out.println("\nAfter deleting the second user");
        user = repo.getByEmail("vasya@email.com");
        repo.delete(user.getUserId());
        printList(repo.getAll());
    }

    private static void testSocialProfiles() {
        UserRepository urepo = context.getBean(UserRepository.class);
        SocialServiceRepository srepo = context.getBean(SocialServiceRepository.class);
        SocialProfileRepository prepo = context.getBean(SocialProfileRepository.class);
        System.out.println("\nTesting social profiles\n**************************************");
        System.out.println("Create new user");
        User user = new User("john@email.com", "12345", "John", "Brown", null, false);
        urepo.save(user);
        printList(urepo.getAll());
        System.out.println("Create new services");
        SocialService facebook = new SocialService("facebook", "dfdfgdf", "dfgdgdgggg"),
                google = new SocialService("google", "jhkjhkjhkh", "jhkjhkhk");
        srepo.save(facebook);
        srepo.save(google);
        printList(srepo.getAll());
        System.out.println("Create social profiles for user");
        prepo.save(new SocialProfile(user.getUserId(), facebook.getServiceId(), "qwerty"));
        prepo.save(new SocialProfile(user.getUserId(), google.getServiceId(), "rewqy"));
        printList(urepo.getProfiles(user));
        System.out.println("Delete user");
        urepo.delete(user.getUserId());
        System.out.println("Social profiles list after user deleting");
        printList(prepo.getAll());
        srepo.delete(facebook.getServiceId());
        srepo.delete(google.getServiceId());
    }
}
