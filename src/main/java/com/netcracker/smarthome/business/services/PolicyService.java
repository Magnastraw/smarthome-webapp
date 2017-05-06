package com.netcracker.smarthome.business.services;

import com.netcracker.smarthome.ApplicationContextHolder;
import com.netcracker.smarthome.business.endpoints.IListener;
import com.netcracker.smarthome.dal.repositories.PolicyRepository;
import com.netcracker.smarthome.model.entities.Catalog;
import com.netcracker.smarthome.model.entities.Policy;
import com.netcracker.smarthome.model.entities.SmartObject;
import com.netcracker.smarthome.model.enums.PolicyStatus;
import com.netcracker.smarthome.dal.repositories.*;
import com.netcracker.smarthome.model.entities.Action;
import com.netcracker.smarthome.model.entities.Condition;
import com.netcracker.smarthome.model.entities.Rule;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.Set;

import java.util.List;

@Service
public class PolicyService {
    private Set<IListener> listeners;
    private PolicyRepository policyRepository;
    private RuleRepository ruleRepository;
    private ActionRepository actionRepository;
    private ConditionRepository conditionRepository;

    @Autowired
    public PolicyService (PolicyRepository policyRepository, RuleRepository ruleRepository, ActionRepository actionRepository, ConditionRepository conditionRepository) {
        this.policyRepository = policyRepository;
        this.ruleRepository = ruleRepository;
        this.actionRepository = actionRepository;
        this.conditionRepository = conditionRepository;
        initListeners();
    }

    private void initListeners() {
        listeners = new HashSet<IListener>();
        Reflections reflections = new Reflections("com.netcracker.smarthome");
        Set<Class<? extends IListener>> subTypes = reflections.getSubTypesOf(IListener.class);
        for (Class cl : subTypes) {
            String[] name = cl.getName().split("(?<=\\.)");
            String beanName = name[name.length-1].substring(0,1).toLowerCase()+name[name.length-1].substring(1);
            try {
                listeners.add((IListener) ApplicationContextHolder.getApplicationContext().getBean(beanName));
            } catch (Exception ex) {}
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

    @Transactional
    public List<SmartObject> getObjectsWithActivePolicies() {
        List<SmartObject> objects = policyRepository.getObjectsWithActivePolicies(),
                inlineObjects = policyRepository.getActiveInlineObjects();
        for (SmartObject object : inlineObjects)
            if (objects.contains(object))
                objects.get(objects.indexOf(object)).getAssignedPolicies().addAll(policyRepository.getActivePoliciesByInlineObject(object.getSmartObjectId()));
            else {
                object.setAssignedPolicies(new HashSet<>(policyRepository.getActivePoliciesByInlineObject(object.getSmartObjectId())));
                objects.add(object);
            }
        return objects;
    }

    @Transactional
    public List<Policy> getActivePoliciesByHome(long homeId) {
        List<Policy> assignedPolicies = policyRepository.getAssignedActivePoliciesByHome(homeId),
                inlinePolicies;
        inlinePolicies = assignedPolicies.isEmpty() ? policyRepository.getInlineActivePoliciesByHome(homeId) : policyRepository.getInlineActivePoliciesByHome(homeId, assignedPolicies);
        for (Policy policy : inlinePolicies)
            policy.getAssignedObjects().addAll(policyRepository.getInlineObjects(policy));
        assignedPolicies.addAll(inlinePolicies);
        return assignedPolicies;
    }

    @Transactional
    public List<Policy> getPoliciesByCatalog(Catalog catalog) {
        return policyRepository.getPoliciesByCatalog(catalog);
    }

    @Transactional(readOnly = true)
    public Policy getActiveInitializedPolicy(long policyId) {
        Policy policy = policyRepository.getInitializedPolicy(policyId);
        if (policy != null && policy.getStatus() == PolicyStatus.ACTIVE) {
            policy.getAssignedObjects().addAll(policyRepository.getInlineObjects(policy));
        }
        if (policy.getAssignedObjects().isEmpty())
            return null;
        return policy;
    }

    @Transactional
    public Policy savePolicy(Policy policy) {
        Policy policyToSave = policyRepository.update(policy);
        onSaveOrUpdate(policyToSave.getPolicyId());
        return policyToSave;
    }

    @Transactional
    public void deletePolicy(long policyId) {
        policyRepository.delete(policyId);
        onSaveOrUpdate(policyId);
    }

    @Transactional
    public Rule saveRule(Rule rule) {
        Rule updatedRule = ruleRepository.update(rule);
        onSaveOrUpdate(updatedRule.getPolicy().getPolicyId());
        return updatedRule;
    }

    @Transactional
    public void deleteRule(Rule rule) {
        ruleRepository.delete(rule.getRuleId());
        onSaveOrUpdate(rule.getPolicy().getPolicyId());
    }

    @Transactional
    public Action saveAction(Action action) {
        Action savedAction = actionRepository.update(action);
        onSaveOrUpdate(savedAction.getRule().getPolicy().getPolicyId());
        return savedAction;
    }

    @Transactional
    public void deleteAction(Action action) {
        ruleRepository.delete(action.getActionId());
        onSaveOrUpdate(action.getRule().getPolicy().getPolicyId());
    }

    @Transactional
    public Condition saveCondition(Condition cndition) {
        Condition savedCondition = conditionRepository.update(cndition);
        onSaveOrUpdate(savedCondition.getRule().getPolicy().getPolicyId());
        return savedCondition;
    }

    @Transactional
    public void deleteCondition(Condition condition) {
        ruleRepository.delete(condition.getNodeId());
        onSaveOrUpdate(condition.getRule().getPolicy().getPolicyId());
    }
}
