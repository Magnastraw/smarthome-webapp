package com.netcracker.smarthome.business.services;

import com.netcracker.smarthome.business.auth.UserExistsException;
import com.netcracker.smarthome.model.entities.SmartHome;
import com.netcracker.smarthome.model.entities.User;
import com.netcracker.smarthome.model.enums.AuthService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistryService {
    private final HomeService homeService;
    private final UserService userService;

    @Autowired
    public RegistryService(HomeService homeService, UserService userService) {
        this.userService = userService;
        this.homeService = homeService;
    }

    @Transactional
    public void simpleRegister(User user) throws UserExistsException {
        if (userService.userExists(user.getEmail()))
            throw new UserExistsException(String.format("User with email '%s' already exists!", user.getEmail()));
        user.setEncrPassword(DigestUtils.md5Hex(user.getEncrPassword()));
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
            user.setEncrPassword(DigestUtils.md5Hex(user.getEncrPassword()));
            user = userService.createUserWithSocialProfile(user, socialId, service);
            homeService.createHome(new SmartHome("Default home", " ", user));
        }
        return user;
    }
}
