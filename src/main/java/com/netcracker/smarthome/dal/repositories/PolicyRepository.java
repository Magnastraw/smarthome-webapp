package com.netcracker.smarthome.dal.repositories;

import com.netcracker.smarthome.model.entities.Catalog;
import com.netcracker.smarthome.model.entities.Policy;
import com.netcracker.smarthome.model.entities.SmartObject;
import com.netcracker.smarthome.model.enums.PolicyStatus;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;
import java.util.Set;

@Repository
public class PolicyRepository extends EntityRepository<Policy> {
    public PolicyRepository() {
        super(Policy.class);
    }

    public List<Policy> getAssignedActivePoliciesByHome(long homeId) {
        Query query = getManager().createQuery("select distinct p from Policy p join fetch p.assignedObjects obj join fetch p.rules r join fetch r.conditions c join fetch r.actions where c.parentNode is null and p.catalog.smartHome.smartHomeId=:home and p.status=:status");
        query.setParameter("home", homeId);
        query.setParameter("status", PolicyStatus.ACTIVE);
        return query.getResultList();
    }

    public List<Policy> getInlineActivePoliciesByHome(long homeId) {
        Query query = getManager().createQuery("select distinct p from Policy p join p.rules r join r.conditions c join c.conditionParams cp where p.catalog.smartHome.smartHomeId=:home and p.status=:status and lower(cp.name)='object'");
        query.setParameter("home", homeId);
        query.setParameter("status", PolicyStatus.ACTIVE);
        List<Policy> nonInitializedPolicies = query.getResultList();
        return nonInitializedPolicies.isEmpty() ? nonInitializedPolicies : initializeWithAssignments(nonInitializedPolicies);
    }

    public List<Policy> getInlineActivePoliciesByHome(long homeId, List<Policy> excluded) {
        Query query = getManager().createQuery("select distinct p from Policy p join p.rules r join r.conditions c join c.conditionParams cp where p.catalog.smartHome.smartHomeId=:home and p not in :excluded and p.status=:status and lower(cp.name)='object'");
        query.setParameter("home", homeId);
        query.setParameter("status", PolicyStatus.ACTIVE);
        query.setParameter("excluded", excluded);
        List<Policy> nonInitializedPolicies = query.getResultList();
        return nonInitializedPolicies.isEmpty() ? nonInitializedPolicies : initializeWithAssignments(nonInitializedPolicies);
    }

    public List<Policy> getAssignedActivePolicies() {
        Query query = getManager().createQuery("select distinct p from Policy p join fetch p.assignedObjects obj join fetch p.rules r join fetch r.conditions c join fetch r.actions where c.parentNode is null and p.status=:status");
        query.setParameter("status", PolicyStatus.ACTIVE);
        return query.getResultList();
    }

    public List<Policy> getInlineActivePolicies() {
        Query query = getManager().createQuery("select distinct p from Policy p join p.rules r join r.conditions c join c.conditionParams cp where p.status=:status and lower(cp.name)='object'");
        query.setParameter("status", PolicyStatus.ACTIVE);
        List<Policy> nonInitializedPolicies = query.getResultList();
        return nonInitializedPolicies.isEmpty() ? nonInitializedPolicies : initializeWithAssignments(nonInitializedPolicies);
    }

    public List<Policy> getInlineActivePolicies(List<Policy> excluded) {
        Query query = getManager().createQuery("select distinct p from Policy p join p.rules r join r.conditions c join c.conditionParams cp where p not in :excluded and p.status=:status and lower(cp.name)='object'");
        query.setParameter("status", PolicyStatus.ACTIVE);
        query.setParameter("excluded", excluded);
        List<Policy> nonInitializedPolicies = query.getResultList();
        return nonInitializedPolicies.isEmpty() ? nonInitializedPolicies : initializeWithAssignments(nonInitializedPolicies);
    }

    public Policy getInitializedPolicy(long policyId) {
        Query query = getManager().createQuery("select distinct p from Policy p left join fetch p.assignedObjects join fetch p.rules r join fetch r.actions join fetch r.conditions c where p.policyId=:id and c.parentNode is null");
        query.setParameter("id", policyId);
        List<Policy> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    public List<SmartObject> getInlineObjects(Policy policy) {
        Query query = getManager().createQuery("select obj from ConditionParam cp join SmartObject obj on cp.name='object' and cp.value=trim(function('to_char', obj.smartObjectId, '999')) and cp.condition.rule.policy=:policy");
        query.setParameter("policy", policy);
        return query.getResultList();
    }

    private List<Policy> initializeWithoutAssignments(List<Policy> nonInitializedPolicies) {
        Query query = getManager().createQuery("select distinct p from Policy p join fetch p.rules r join fetch r.actions join fetch r.conditions c where p in :policies and c.parentNode is null");
        query.setParameter("policies", nonInitializedPolicies);
        return query.getResultList();
    }

    private List<Policy> initializeWithAssignments(List<Policy> nonInitializedPolicies) {
        Query query = getManager().createQuery("select distinct p from Policy p left join fetch p.assignedObjects join fetch p.rules r join fetch r.actions join fetch r.conditions c where p in :policies and c.parentNode is null");
        query.setParameter("policies", nonInitializedPolicies);
        return query.getResultList();
    }

    public List<Policy> getPoliciesByCatalog(Catalog catalog) {
        Query query = getManager().createQuery("select p from Policy p where p.catalog.catalogId=:catalog");
        query.setParameter("catalog", catalog.getCatalogId());
        return query.getResultList();
    }

    public Set<Policy> getPoliciesByObject(SmartObject object) {
        Query query = getManager().createQuery("select obj from SmartObject obj left join fetch obj.assignedPolicies p where obj=:object");
        query.setParameter("object", object);
        List<SmartObject> result = query.getResultList();
        return result.get(0).getAssignedPolicies();
    }

    public void saveAssignment(Policy policy, SmartObject object) {
        Query query = getManager().createNativeQuery("INSERT INTO assignments (policy_id, object_id) VALUES (:policy, :object)");
        query.setParameter("policy", policy.getPolicyId());
        query.setParameter("object", object.getSmartObjectId());
        query.executeUpdate();
    }

    public void deleteAssignment(Policy policy, SmartObject object) {
        Query query = getManager().createNativeQuery("DELETE FROM assignments WHERE policy_id=:policy AND object_id=:object");
        query.setParameter("policy", policy.getPolicyId());
        query.setParameter("object", object.getSmartObjectId());
        query.executeUpdate();
    }
}
