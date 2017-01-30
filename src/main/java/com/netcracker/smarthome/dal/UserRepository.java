package com.netcracker.smarthome.dal;

import com.netcracker.smarthome.model.entities.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository extends EntityRepository<User> {
    @Override
    public void setEntityClass() {
        entityClass = User.class;
    }
}
