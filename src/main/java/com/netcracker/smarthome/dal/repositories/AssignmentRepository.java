package com.netcracker.smarthome.dal.repositories;

import com.netcracker.smarthome.model.entities.Assignment;
import org.springframework.stereotype.Repository;

@Repository
public class AssignmentRepository extends EntityRepository<Assignment> {
    public AssignmentRepository() {
        super(Assignment.class);
    }
}
