package com.netcracker.smarthome.dal;

import com.netcracker.smarthome.dal.repositories.CatalogRepository;
import com.netcracker.smarthome.dal.repositories.SocialProfileRepository;
import com.netcracker.smarthome.dal.repositories.SocialServiceRepository;
import com.netcracker.smarthome.dal.repositories.UserRepository;
import com.netcracker.smarthome.model.entities.Catalog;
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
        testCatalogs();
        testSocialProfiles();
    }

    private static <T> void printList(List<T> list) {
        if (list.isEmpty())
            System.out.println("\tEmpty list");
        else {
            for (T obj : list
                    ) {
                System.out.println("\t" + obj);
            }
        }
    }

    private static void testUsers() {
        UserRepository repo = context.getBean(UserRepository.class);
        System.out.println("\nTesting users\n**************************************");
        System.out.println("Create new users");
        repo.save(new User("John", "Brown", null, "john@email.com", "12345", false));
        repo.save(new User("Vasya", "Pupkin", null, "vasya@email.com", "12345", false));
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

    private static void testCatalogs() {
        CatalogRepository repo = context.getBean(CatalogRepository.class);
        System.out.println("\nTesting catalogs\n**************************************");
        System.out.println("Create new catalogs");
        Catalog root = new Catalog("catalog 1", null),
                catalog1 = new Catalog("catalog 1.1", root),
                catalog2 = new Catalog("catalog 1.2", root),
                catalog3 = new Catalog("catalog 1.2.1", catalog2);
        repo.save(root);
        repo.save(catalog1);
        repo.save(catalog2);
        repo.save(catalog3);
        printList(repo.getAll());
        System.out.println("\nAfter updating catalogs");
        catalog1.setParentCatalog(null);
        catalog2.setParentCatalog(null);
        repo.update(catalog1);
        repo.update(catalog2);
        printList(repo.getAll());
        System.out.println("\nAfter deleting catalogs");
        repo.delete(catalog3.getCatalogId());
        repo.delete(catalog2.getCatalogId());
        repo.delete(catalog1.getCatalogId());
        repo.delete(root.getCatalogId());
        printList(repo.getAll());
    }

    private static void testSocialProfiles() {
        UserRepository urepo = context.getBean(UserRepository.class);
        SocialServiceRepository srepo = context.getBean(SocialServiceRepository.class);
        SocialProfileRepository prepo = context.getBean(SocialProfileRepository.class);
        System.out.println("\nTesting social profiles\n**************************************");
        System.out.println("Create new user");
        User user = new User("John", "Brown", null, "john@email.com", "12345", false);
        urepo.save(user);
        printList(urepo.getAll());
        System.out.println("Create new services");
        SocialService facebook = new SocialService("facebook", "dfdfgdf", "dfgdgdgggg"),
                google = new SocialService("google", "jhkjhkjhkh", "jhkjhkhk");
        srepo.save(facebook);
        srepo.save(google);
        printList(srepo.getAll());
        System.out.println("Create social profiles for user");
        prepo.save(new SocialProfile("qwerty", user.getUserId(), facebook.getServiceId()));
        prepo.save(new SocialProfile("rewqy", user.getUserId(), google.getServiceId()));
        printList(urepo.getProfiles(user));
        System.out.println("Delete user");
        urepo.delete(user.getUserId());
        System.out.println("Social profiles list after user deleting");
        printList(prepo.getAll());
        srepo.delete(facebook.getServiceId());
        srepo.delete(google.getServiceId());
    }
}
