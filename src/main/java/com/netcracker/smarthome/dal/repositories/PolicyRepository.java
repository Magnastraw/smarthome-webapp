package com.netcracker.smarthome.dal.repositories;

import com.netcracker.smarthome.model.entities.Policy;
import com.netcracker.smarthome.model.entities.Rule;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class PolicyRepository extends EntityRepository<Policy> {
    public PolicyRepository() {
        super(Policy.class);
    }

    public List<Rule> getRules(Policy policy) {
        Query query = manager.createQuery("select r from Rule r where r.policy.policyId = :policyId");
        query.setParameter("policyId", policy.getPolicyId());
        return query.getResultList();
    }
}
