package com.netcracker.smarthome.business.policy.transform;

import com.netcracker.smarthome.business.policy.exec.nodes.PolicyNode;
import com.netcracker.smarthome.business.policy.exec.nodes.RuleNode;
import com.netcracker.smarthome.model.entities.Policy;
import com.netcracker.smarthome.model.entities.Rule;

import java.util.LinkedList;
import java.util.List;

public class PolicyConverter {
    private RuleConverter ruleConverter = new RuleConverter();

    public PolicyNode convert(Policy policy) {
        PolicyNode policyNode = new PolicyNode(policy.getPolicyId(), policy.getRules().size());
        List<RuleNode> ruleNodes = new LinkedList<RuleNode>();
        for (Rule rule : policy.getRules()
                ) {
            ruleNodes.add(ruleConverter.convert(rule, policyNode));
        }
        //TODO: add leaf conditions
        return policyNode;
    }

}
