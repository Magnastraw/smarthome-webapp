package com.netcracker.smarthome.business.services;

import com.netcracker.smarthome.dal.repositories.SocialProfileRepository;
import com.netcracker.smarthome.dal.repositories.SocialServiceRepository;
import com.netcracker.smarthome.dal.repositories.UserRepository;
import com.netcracker.smarthome.model.entities.SocialProfile;
import com.netcracker.smarthome.model.entities.User;
import com.netcracker.smarthome.model.enums.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final SocialProfileRepository profileRepository;
    private final SocialServiceRepository serviceRepository;

    @Autowired
    public UserService(UserRepository userRepository, SocialProfileRepository profileRepository, SocialServiceRepository serviceRepository) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.serviceRepository = serviceRepository;
    }

    @Transactional
    public boolean userExists(String email) {
        return getUserByEmail(email) != null;
    }

    @Transactional
    public User getUserByEmail(String email) {
        return userRepository.getByEmail(email);
    }

    @Transactional
    public User getUserBySocialId(String socialId, AuthService service) {
        return userRepository.getBySocialId(socialId, service);
    }

    @Transactional
    public boolean userExists(String socialId, AuthService service) {
        return getUserBySocialId(socialId, service) != null;
    }

    @Transactional
    public User saveUser(User user) {
        return userRepository.update(user);
    }

    @Transactional
    public void createSocialProfile(User user, String socialId, AuthService service) {
        SocialProfile profile = new SocialProfile(user.getUserId(), serviceRepository.getByServiceType(service).getServiceId(), socialId);
        profileRepository.save(profile);
    }

    @Transactional
    public User createUserWithSocialProfile(User user, String socialId, AuthService service) {
        boolean needEmail = user.getEmail() == null;
        if (needEmail) {
            user.setEmail("user@email.com");
            user = saveUser(user);
            user.setEmail(String.format("user%d@email.com", user.getUserId()));
        }
        user = saveUser(user);
        createSocialProfile(user, socialId, service);
        return user;
    }
}
