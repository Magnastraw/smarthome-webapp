package com.netcracker.smarthome.business.policy.transform.core;

import com.netcracker.smarthome.business.policy.core.nodes.PolicyNode;
import com.netcracker.smarthome.business.policy.core.nodes.ifaces.EngineNode;
import com.netcracker.smarthome.model.entities.Policy;
import com.netcracker.smarthome.model.entities.Rule;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class PolicyConverter {
    private RuleConverter ruleConverter;

    public PolicyConverter() {
        this.ruleConverter = new RuleConverter();
    }

    public PolicyNode convert(Policy policy) {
        PolicyNode policyNode = new PolicyNode(policy.getPolicyId(), policy.getRules().size());
        Map<Long, EngineNode> children = new HashMap<>();
        for (Rule rule : policy.getRules())
            ruleConverter.convert(rule, policyNode, policyNode, children);
        policyNode.setChildren(children);
        return policyNode;
    }
}
