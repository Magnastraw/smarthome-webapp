package com.netcracker.smarthome.business.services;

import com.netcracker.smarthome.ApplicationContextHolder;
import com.netcracker.smarthome.business.endpoints.IListener;
import com.netcracker.smarthome.dal.repositories.*;
import com.netcracker.smarthome.model.entities.Action;
import com.netcracker.smarthome.model.entities.Condition;
import com.netcracker.smarthome.model.entities.Policy;
import com.netcracker.smarthome.model.entities.Rule;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.Set;

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

    public void onSaveOrUpdate(Object object) {
        for(IListener listener : listeners) {
            listener.onSaveOrUpdate(object);
        }
    }


    @Transactional
    public void savePolicy(Policy policy) {
        policyRepository.save(policy);
        onSaveOrUpdate(policy);
    }

    @Transactional
    public Policy updatePolicy(Policy policy) {
        Policy updatedPolicy = policyRepository.update(policy);
        onSaveOrUpdate(updatedPolicy);
        return updatedPolicy;
    }

    @Transactional(readOnly = true)
    public Policy getPolicyById(long policyId) {
        return policyRepository.get(policyId);
    }

    @Transactional
    public void saveRule(Rule rule) {
        ruleRepository.save(rule);
        onSaveOrUpdate(rule);
    }

    @Transactional
    public Rule updateRule(Rule rule) {
        Rule updatedRule = ruleRepository.update(rule);
        onSaveOrUpdate(updatedRule);
        return updatedRule;
    }

    @Transactional
    public void saveAction(Action action) {
        actionRepository.save(action);
        onSaveOrUpdate(action);
    }

    @Transactional
    public Action updateAction(Action action) {
        Action updatedAction = actionRepository.update(action);
        onSaveOrUpdate(updatedAction);
        return updatedAction;
    }

    @Transactional
    public void saveCondition(Condition condition) {
        conditionRepository.save(condition);
        onSaveOrUpdate(condition);
    }

    @Transactional
    public Condition updateCondition(Condition cndition) {
        Condition updatedCondition = conditionRepository.update(cndition);
        onSaveOrUpdate(updatedCondition);
        return updatedCondition;
    }
}
