package com.netcracker.smarthome.business.policy.services;

import com.netcracker.smarthome.dal.repositories.RuleRepository;
import com.netcracker.smarthome.model.entities.Policy;
import com.netcracker.smarthome.model.entities.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RuleService {
    private final RuleRepository ruleRepository;

    @Autowired
    public RuleService(RuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }

    @Transactional
    public void deleteRule(long ruleId) {
        ruleRepository.delete(ruleId);
    }

    @Transactional
    public Rule getRuleById(long ruleId) {
        return ruleRepository.get(ruleId);
    }

    @Transactional
    public void createRule(Rule rule) {
        ruleRepository.save(rule);
    }

    @Transactional
    public Rule updateRule(Rule rule) {
        return ruleRepository.update(rule);
    }

    @Transactional
    public List<Rule> getRulesByPolicy(Policy policy) {
        return ruleRepository.getRulesByPolicy(policy);
    }
}
