package com.netcracker.smarthome.business.services;

import com.netcracker.smarthome.business.auth.UserExistsException;
import com.netcracker.smarthome.business.policy.actions.SendNotificationAction;
import com.netcracker.smarthome.model.entities.SmartHome;
import com.netcracker.smarthome.model.entities.User;
import com.netcracker.smarthome.model.enums.AuthService;
import com.netcracker.smarthome.model.enums.Channel;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistryService {
    private static final Logger LOG = LoggerFactory.getLogger(RegistryService.class);
    private final HomeService homeService;
    private final UserService userService;
    private final NotificationService notificationService;

    @Autowired
    public RegistryService(HomeService homeService, UserService userService, NotificationService notificationService) {
        this.userService = userService;
        this.homeService = homeService;
        this.notificationService = notificationService;
    }

    @Transactional
    public void simpleRegister(User user) throws UserExistsException {
        if (userService.userExists(user.getEmail()))
            throw new UserExistsException(String.format("User with email '%s' already exists!", user.getEmail()));
        user.setEncrPassword(DigestUtils.md5Hex(user.getEncrPassword()));
        user.setPreferChannel(Channel.Email);
        user = userService.saveUser(user);
        homeService.createHome(new SmartHome("Default home", " ", user));
    }

    @Transactional
    public User socialRegister(User user, String socialId, AuthService service) throws UserExistsException {
        if (userService.userExists(socialId, service))
            throw new UserExistsException(String.format("%s social profile with id=%s already exists!", service.toString(), socialId));
        if (user.getEmail() != null && userService.userExists(user.getEmail())) {
            userService.createSocialProfile((user = userService.getUserByEmail(user.getEmail())), socialId, service);
        } else {
            String password = generateRandomPassword();
            user.setEncrPassword(DigestUtils.md5Hex(password));
            user.setPreferChannel(Channel.Email);
            user = userService.createUserWithSocialProfile(user, socialId, service);
            SmartHome defaultHome = new SmartHome("Default home", " ", user);
            homeService.createHome(defaultHome);
            try {
                notificationService.sendNotification(String.format("%s %s, you signed up via %s on Smarthome service. \nYour password: %s\nYou can change it later in profile settings.", user.getFirstName(), user.getLastName(), service, password), defaultHome, Channel.Email, null);
            } catch (Exception ex) {
                LOG.info("Error during notification sending");
            }
        }
        return user;
    }

    private String generateRandomPassword() {
        String characters = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        return RandomStringUtils.random(10, characters);
    }
}
