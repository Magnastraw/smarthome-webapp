package com.netcracker.smarthome.business.services;

import com.netcracker.smarthome.ApplicationContextHolder;
import com.netcracker.smarthome.business.endpoints.IListener;
import com.netcracker.smarthome.dal.repositories.ActionRepository;
import com.netcracker.smarthome.dal.repositories.ConditionRepository;
import com.netcracker.smarthome.dal.repositories.PolicyRepository;
import com.netcracker.smarthome.dal.repositories.RuleRepository;
import com.netcracker.smarthome.model.entities.*;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PolicyService {
    private Set<IListener> listeners;
    private PolicyRepository policyRepository;
    private RuleRepository ruleRepository;
    private ActionRepository actionRepository;
    private ConditionRepository conditionRepository;

    @Autowired
    public PolicyService(PolicyRepository policyRepository, RuleRepository ruleRepository, ActionRepository actionRepository, ConditionRepository conditionRepository) {
        this.policyRepository = policyRepository;
        this.ruleRepository = ruleRepository;
        this.actionRepository = actionRepository;
        this.conditionRepository = conditionRepository;
        initListeners();
    }

    private void initListeners() {
        listeners = new HashSet<>();
        Reflections reflections = new Reflections("com.netcracker.smarthome");
        Set<Class<? extends IListener>> subTypes = reflections.getSubTypesOf(IListener.class);
        for (Class cl : subTypes) {
            String[] name = cl.getName().split("(?<=\\.)");
            String beanName = name[name.length - 1].substring(0, 1).toLowerCase() + name[name.length - 1].substring(1);
            try {
                listeners.add((IListener) ApplicationContextHolder.getApplicationContext().getBean(beanName));
            } catch (Exception ex) {
            }
        }
    }

    public void addListener(IListener listener) {
        listeners.add(listener);
    }

    public void removeListener(IListener listener) {
        listeners.remove(listener);
    }

    private void onSaveOrUpdate(Object object) {
        for (IListener listener : listeners) {
            listener.onSaveOrUpdate(object);
        }
    }

    public List<Rule> getRules(long policyId) {
        return policyRepository.gitInitializedRules(policyId);
    }

    public Rule getRule(long ruleId) {
        return policyRepository.gitInitializedRule(ruleId);
    }

    @Transactional(readOnly = true)
    public List<Policy> getActivePoliciesByHome(long homeId) {
        List<Policy> inlinePolicies = policyRepository.getInlineActivePoliciesByHome(homeId),
                assignedPolicies;
        assignedPolicies = inlinePolicies.isEmpty() ? policyRepository.getAssignedActivePoliciesByHome(homeId) : policyRepository.getAssignedActivePoliciesByHome(homeId, inlinePolicies);
        for (Policy policy : inlinePolicies) {
            policy.getAssignedObjects().addAll(policyRepository.getInlineObjects(policy));
        }
        assignedPolicies.addAll(inlinePolicies.stream().filter(policy -> !policy.getAssignedObjects().isEmpty()).collect(Collectors.toSet()));
        return assignedPolicies;
    }

    @Transactional(readOnly = true)
    public List<Policy> getActivePolicies() {
        List<Policy> inlinePolicies = policyRepository.getInlineActivePolicies(),
                assignedPolicies;
        assignedPolicies = inlinePolicies.isEmpty() ? policyRepository.getAssignedActivePolicies() : policyRepository.getAssignedActivePolicies(inlinePolicies);
        for (Policy policy : inlinePolicies) {
            policy.getAssignedObjects().addAll(policyRepository.getInlineObjects(policy));
        }
        assignedPolicies.addAll(inlinePolicies.stream().filter(policy -> !policy.getAssignedObjects().isEmpty()).collect(Collectors.toSet()));
        return assignedPolicies;
    }

    @Transactional(readOnly = true)
    public List<Policy> getPoliciesByCatalog(Catalog catalog) {
        return policyRepository.getPoliciesByCatalog(catalog);
    }

    @Transactional(readOnly = true)
    public Policy getActiveInitializedPolicy(long policyId) {
        Policy policy = policyRepository.getActiveInitializedPolicy(policyId);
        if (policy == null)
            return null;
        policy.getAssignedObjects().addAll(policyRepository.getInlineObjects(policy));
        if (policy.getAssignedObjects().isEmpty())
            return null;
        return policy;
    }

    @Transactional(readOnly = true)
    public List<Policy> getActiveInlinePolicies(List<SmartObject> inlineAssignedObjects) {
        return policyRepository.getInlineActivePolicies(inlineAssignedObjects.stream().map(object -> ((Long) object.getSmartObjectId()).toString()).collect(Collectors.toSet()), false);
    }

    @Transactional(readOnly = true)
    public List<Policy> getActivePoliciesByObject(SmartObject object) {
        List<Policy> inlinePolicies = policyRepository.getActivePoliciesByInlineObject(object),
                assignedPolicies = inlinePolicies.isEmpty() ? policyRepository.getActivePoliciesByAssignedObject(object) : policyRepository.getActivePoliciesByAssignedObject(object, inlinePolicies);
        inlinePolicies.addAll(assignedPolicies);
        return inlinePolicies;
    }

    @Transactional(readOnly = true)
    public Set<Policy> getPoliciesByObject(SmartObject object) {
        return policyRepository.getPoliciesByObject(object);
    }

    public void saveAssignment(SmartObject object, Policy policy) {
        policyRepository.saveAssignment(policy, object);
        onSaveOrUpdate(policy);
    }

    public void deleteAssignment(SmartObject object, Policy policy) {
        policyRepository.deleteAssignment(policy, object);
        onSaveOrUpdate(policy);
    }

    public Policy savePolicy(Policy policy) {
        Policy updatedPolicy = policyRepository.update(policy);
        onSaveOrUpdate(updatedPolicy);
        return updatedPolicy;
    }

    public void deletePolicy(long policyId) {
        Policy oldPolicy = policyRepository.get(policyId);
        policyRepository.delete(policyId);
        onSaveOrUpdate(oldPolicy);
    }

    public Rule saveRule(Rule rule) {
        Rule updatedRule = ruleRepository.update(rule);
        return updatedRule;
    }

    public void deleteRule(Rule rule) {
        ruleRepository.delete(rule.getRuleId());
    }

    public Action saveAction(Action action) {
        Action savedAction = actionRepository.update(action);
        return savedAction;
    }

    public void deleteAction(Action action) {
        ruleRepository.delete(action.getActionId());
    }

    public Condition saveCondition(Condition condition) {
        Condition savedCondition = conditionRepository.update(condition);
        return savedCondition;
    }

    public void deleteCondition(Condition condition) {
        ruleRepository.delete(condition.getNodeId());
    }
}
