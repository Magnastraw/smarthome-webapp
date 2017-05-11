package com.netcracker.smarthome.dal.repositories;

import com.netcracker.smarthome.model.entities.Policy;
import com.netcracker.smarthome.model.entities.Rule;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class RuleRepository extends EntityRepository<Rule> {
    public RuleRepository() {
        super(Rule.class);
    }

    public List<Rule> getRulesByPolicy(Policy policy) {
        Query query = getManager().createQuery("select r from Rule r where r.ruleId=:policyId");
        query.setParameter("policyId", policy.getPolicyId());
        return query.getResultList();
    }
}
