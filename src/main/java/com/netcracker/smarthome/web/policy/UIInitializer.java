package com.netcracker.smarthome.web.policy;

import com.netcracker.smarthome.business.services.PolicyService;
import com.netcracker.smarthome.model.entities.Rule;
import com.netcracker.smarthome.web.policy.components.UIRule;
import com.netcracker.smarthome.web.policy.components.transform.RuleConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UIInitializer {
    private final PolicyService policyService;
    private final RuleConverter ruleConverter;

    @Autowired
    public UIInitializer(PolicyService policyService) {
        this.policyService = policyService;
        ruleConverter = new RuleConverter();
    }

    public UIRule buildRule(long ruleId) {
        Rule dbRule = policyService.getRule(ruleId);
        return ruleConverter.convert(dbRule);
    }
}
