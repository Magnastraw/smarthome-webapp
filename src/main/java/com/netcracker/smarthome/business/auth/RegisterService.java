package com.netcracker.smarthome.business.auth;

import com.netcracker.smarthome.dal.repositories.UserRepository;
import com.netcracker.smarthome.model.entities.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisterService {
    private final UserRepository repository;

    @Autowired
    public RegisterService(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void simpleRegister(User user) throws UserExistsException {
        if (repository.getByEmail(user.getEmail()) != null)
            throw new UserExistsException("User with such email already exists!");
        user.setEncrPassword(DigestUtils.md5Hex(user.getEncrPassword()));
        repository.save(user);
    }
}
