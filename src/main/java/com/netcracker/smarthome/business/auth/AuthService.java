package com.netcracker.smarthome.business.auth;

import com.netcracker.smarthome.dal.repositories.UserRepository;
import com.netcracker.smarthome.model.entities.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    UserRepository repository;

    public User authenticateByCredentials(String email, String password) {
        User user = repository.getByEmail(email.toLowerCase());
        String pass=DigestUtils.md5Hex(password);
        return user != null && user.getEncrPassword().equals(pass) ? user : null;
    }

    public boolean register(User user) {
        if (repository.getByEmail(user.getEmail()) != null)
            return false;
        user.setEncrPassword(DigestUtils.md5Hex(user.getEncrPassword()));
        repository.save(user);
        return true;
    }
}
