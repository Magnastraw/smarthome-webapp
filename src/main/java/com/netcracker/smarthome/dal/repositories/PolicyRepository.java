package com.netcracker.smarthome.dal.repositories;

import com.netcracker.smarthome.model.entities.Policy;
import com.netcracker.smarthome.model.entities.Rule;
import com.netcracker.smarthome.model.entities.SmartHome;
import com.netcracker.smarthome.model.entities.SmartObject;
import com.netcracker.smarthome.model.enums.PolicyStatus;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class PolicyRepository extends EntityRepository<Policy> {
    public PolicyRepository() {
        super(Policy.class);
    }

    public List<Rule> getRules(Policy policy) {
        Query query = getManager().createQuery("select r from Rule r where r.policy.policyId = :policyId");
        query.setParameter("policyId", policy.getPolicyId());
        return query.getResultList();
    }
    public List<Policy> getActivePolicies() {
        Query query = getManager().createQuery("select p from Policy p where p.status = :policyStatus");
        query.setParameter("policyStatus", PolicyStatus.ACTIVE);
        return query.getResultList();
    }

    public List<Policy> getActivePoliciesByHome(SmartHome home) {
        Query query = getManager().createQuery("select p from Policy p join Catalog c on p.catalog.catalogId = c.catalogId where c.smartHome.id = :smartHomeId and p.status = :policyStatus");
        query.setParameter("smartHomeId", home.getSmartHomeId());
        query.setParameter("policyStatus", PolicyStatus.ACTIVE);
        return query.getResultList();
    }

    public List<Policy> getActivePoliciesByObject(SmartObject smartObject) {
        Query query = getManager().createNativeQuery("select p.* from policies p join assignments a on p.id = a.policyId where a.objectId = :smartObjectId and p.status = :policyStatus " +
                "union select p.* from policies p join rules r on p.policy_id = r.policy_id join conditions c on r.rule_id = c.rule_id join condition_params cp on c.condition_id = cp.condition_id " +
                "where cp.name = 'object' and cp.value = :smartObjectId and p.status = :policyStatus");
        query.setParameter("smartObjectId", Long.toString(smartObject.getSmartObjectId()));
        query.setParameter("policyStatus", PolicyStatus.ACTIVE);
        return query.getResultList();
    }

    public List<SmartObject> getObjectsByPolicy(Policy policy) {
        Query query = getManager().createNativeQuery("select o.* from objects o join assignments a on o.smart_object_id = a.object_id where a.policy_id = :policyId " +
                "union select o.* from rules r join conditions c on r.rule_id = c.rule_id join condition_params cp on c.condition_id = cp.condition_id and cp.name = 'object' join objects o on to_char(o.smart_object_id) = cp.value " +
                "where r.policy_id = :policyId");
        query.setParameter("policyId", policy.getPolicyId());
        return query.getResultList();
    }
}
