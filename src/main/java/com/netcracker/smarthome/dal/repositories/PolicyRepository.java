package com.netcracker.smarthome.dal.repositories;

import com.netcracker.smarthome.model.entities.Catalog;
import com.netcracker.smarthome.model.entities.Policy;
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

    public List<Policy> getPoliciesByStatus(PolicyStatus status) {
        Query query = getManager().createQuery("select p from Policy p where p.status = :policyStatus");
        query.setParameter("policyStatus", status);
        return query.getResultList();
    }

//    public List<Policy> getActivePoliciesByObject(SmartObject smartObject) {
//        Query query = getManager().createNativeQuery("SELECT p.* FROM policies p JOIN assignments a ON p.id = a.policyId WHERE a.objectId = :smartObjectId AND p.status = :policyStatus " +
//                "UNION SELECT p.* FROM policies p JOIN rules r ON p.policy_id = r.policy_id JOIN conditions c ON r.rule_id = c.rule_id JOIN condition_params cp ON c.node_id = cp.condition_id " +
//                "WHERE cp.name = 'object' AND cp.value = :smartObjectId AND p.status = :policyStatus");
//        query.setParameter("smartObjectId", Long.toString(smartObject.getSmartObjectId()));
//        query.setParameter("policyStatus", PolicyStatus.ACTIVE);
//        return query.getResultList();
//    }
//
//    public List<SmartObject> getObjectsByPolicy(Policy policy) {
//        Query query = getManager().createNativeQuery("SELECT o.* FROM objects o JOIN assignments a ON o.smart_object_id = a.object_id WHERE a.policy_id = :policyId " +
//                "UNION SELECT o.* FROM rules r JOIN conditions c ON r.rule_id = c.rule_id JOIN condition_params cp ON c.node_id = cp.condition_id AND cp.name = 'object' JOIN objects o ON to_char(o.smart_object_id) = cp.value " +
//                "WHERE r.policy_id = :policyId");
//        query.setParameter("policyId", policy.getPolicyId());
//        return query.getResultList();
//    }

    public List<SmartObject> getObjectsWithActivePolicies() {
        Query query = getManager().createQuery("select distinct obj from SmartObject obj join fetch obj.assignedPolicies p join fetch p.rules r join fetch r.conditions c join fetch r.actions where c.parentNode is null and p.status=:status");
        query.setParameter("status", PolicyStatus.ACTIVE);
        return query.getResultList();
    }

    public List<SmartObject> getActivePoliciesInlineObjects() {
        Query query = getManager().createQuery("select obj from ConditionParam cp join SmartObject obj on cp.name='object' and cp.value=function('to_char', obj.smartObjectId, '9') and cp.condition.rule.policy.status=:status");
        query.setParameter("status", PolicyStatus.ACTIVE);
        return query.getResultList();
    }

    public List<Policy> getActivePoliciesByInlineObject(SmartObject object) {
        Query query = getManager().createQuery("select distinct p from Policy p join fetch p.rules r join fetch r.conditions c join fetch r.actions join c.conditionParams cp join SmartObject obj on cp.name='object' and cp.value=function('to_char', obj.smartObjectId, '9') where c.parentNode is null and p.status=:status");
        query.setParameter("status", PolicyStatus.ACTIVE);
        return query.getResultList();
    }

    public List<Policy> getPoliciesByCatalog(Catalog catalog) {
        Query query = getManager().createQuery("select p from Policy p where p.catalog.catalogId=:catalog");
        query.setParameter("catalog", catalog.getCatalogId());
        return query.getResultList();
    }
}
