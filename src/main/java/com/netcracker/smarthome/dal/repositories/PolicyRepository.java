package com.netcracker.smarthome.dal.repositories;

import com.netcracker.smarthome.model.entities.*;
import com.netcracker.smarthome.model.enums.PolicyStatus;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.HashSet;
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

    public List<Policy> getAssignedActivePoliciesByHome(long homeId, List<Policy> excluded) {
        Query query = getManager().createQuery("select distinct p from Policy p join fetch p.assignedObjects obj join fetch p.rules r join fetch r.conditions c join fetch r.actions where c.parentNode is null and p.catalog.smartHome.smartHomeId=:home and p not in :excluded and p.status=:status");
        query.setParameter("home", homeId);
        query.setParameter("status", PolicyStatus.ACTIVE);
        query.setParameter("excluded", excluded);
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

    public List<Policy> getAssignedActivePolicies(List<Policy> excluded) {
        Query query = getManager().createQuery("select distinct p from Policy p join fetch p.assignedObjects obj join fetch p.rules r join fetch r.conditions c join fetch r.actions where p not in :excluded and c.parentNode is null and p.status=:status");
        query.setParameter("status", PolicyStatus.ACTIVE);
        query.setParameter("excluded", excluded);
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

    public List<Policy> getInlineActivePolicies(Set<String> inlineAssignedObjectsIds, boolean withInitialization) {
        Query query = getManager().createQuery("select distinct p from Policy p join p.rules r join r.conditions c join c.conditionParams cp where p.status=:status and lower(cp.name)='object' and cp.value in :objects");
        query.setParameter("status", PolicyStatus.ACTIVE);
        query.setParameter("objects", inlineAssignedObjectsIds);
        List<Policy> nonInitializedPolicies = query.getResultList();
        return nonInitializedPolicies.isEmpty() || !withInitialization ? nonInitializedPolicies : initializeWithAssignments(nonInitializedPolicies);
    }

    public Policy getActiveInitializedPolicy(long policyId) {
        Query query = getManager().createQuery("select distinct p from Policy p left join fetch p.assignedObjects join fetch p.rules r join fetch r.actions join fetch r.conditions c where p.policyId=:id and p.status=:status and c.parentNode is null");
        query.setParameter("id", policyId);
        query.setParameter("status", PolicyStatus.ACTIVE);
        List<Policy> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    public List<SmartObject> getInlineObjects(Policy policy) {
        Query query = getManager().createQuery("select obj from ConditionParam cp join SmartObject obj on cp.name='object' and cp.value=trim(function('to_char', obj.smartObjectId, '9999')) and cp.condition.rule.policy=:policy");
        query.setParameter("policy", policy);
        return query.getResultList();
    }

    public List<Policy> getActivePoliciesByAssignedObject(SmartObject object) {
        Query query = getManager().createQuery("select distinct p from Policy p join p.assignedObjects obj join p.rules r join r.conditions c join r.actions where obj=:object and p.status=:status");
        query.setParameter("status", PolicyStatus.ACTIVE);
        query.setParameter("object", object);
        List<Policy> policies = query.getResultList();
        return policies.isEmpty() ? policies : initializeWithAssignments(policies);
    }

    public List<Policy> getActivePoliciesByAssignedObject(SmartObject object, List<Policy> excluded) {
        Query query = getManager().createQuery("select distinct p from Policy p join p.assignedObjects obj join p.rules r join r.conditions c join r.actions where p not in :excluded and obj=:object and p.status=:status");
        query.setParameter("status", PolicyStatus.ACTIVE);
        query.setParameter("object", object);
        query.setParameter("excluded", excluded);
        List<Policy> policies = query.getResultList();
        return policies.isEmpty() ? policies : initializeWithAssignments(policies);
    }

    public List<Policy> getActivePoliciesByInlineObject(SmartObject object) {
        Query query = getManager().createQuery("select distinct p from Policy p join p.rules r join r.conditions c join r.actions join c.conditionParams cp join SmartObject obj on cp.name='object' and cp.value=trim(function('to_char', :object, '9999')) where p.status=:status");
        query.setParameter("status", PolicyStatus.ACTIVE);
        query.setParameter("object", object);
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
        return result.isEmpty() ? new HashSet<>() : result.get(0).getAssignedPolicies();
    }

    @Transactional
    public void saveAssignment(Policy policy, SmartObject object) {
        Query query = getManager().createNativeQuery("INSERT INTO assignments (policy_id, object_id) VALUES (:policy, :object)");
        query.setParameter("policy", policy.getPolicyId());
        query.setParameter("object", object.getSmartObjectId());
        query.executeUpdate();
    }

    @Transactional
    public void deleteAssignment(Policy policy, SmartObject object) {
        Query query = getManager().createNativeQuery("DELETE FROM assignments WHERE policy_id=:policy AND object_id=:object");
        query.setParameter("policy", policy.getPolicyId());
        query.setParameter("object", object.getSmartObjectId());
        query.executeUpdate();
    }

    public List<Rule> gitRules(long policyId) {
        Query query = getManager().createQuery("select distinct r from Rule r where r.policy.policyId=:id order by r.ruleId");
        query.setParameter("id", policyId);
        return query.getResultList();
    }

    public Rule gitInitializedRule(long ruleId) {
        Query query = getManager().createQuery("select distinct r from Rule r left join fetch r.actions left join fetch r.conditions c where r.ruleId=:id and c.parentNode is null");
        query.setParameter("id", ruleId);
        List<Rule> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }
}
