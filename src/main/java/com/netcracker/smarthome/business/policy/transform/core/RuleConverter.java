package com.netcracker.smarthome.business.policy.transform.core;

import com.netcracker.smarthome.business.policy.core.listeners.RuleCompletionListener;
import com.netcracker.smarthome.business.policy.core.nodes.ActionNode;
import com.netcracker.smarthome.business.policy.core.nodes.RuleNode;
import com.netcracker.smarthome.business.policy.core.nodes.ifaces.EngineNode;
import com.netcracker.smarthome.business.policy.core.nodes.ifaces.PolicyNodesHolder;
import com.netcracker.smarthome.model.entities.Action;
import com.netcracker.smarthome.model.entities.Condition;
import com.netcracker.smarthome.model.entities.Rule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class RuleConverter {
    private ActionConverter actionConverter;
    private ConditionConverter conditionConverter;
    private OperationConverter operationConverter;

    RuleConverter() {
        this.actionConverter = new ActionConverter();
        this.conditionConverter = new ConditionConverter();
        this.operationConverter = new OperationConverter();
    }

    public RuleNode convert(Rule rule, RuleCompletionListener listener, PolicyNodesHolder holder, Map<Long, EngineNode> policyChildren) {
        List<ActionNode> thenActions = new ArrayList<>(), elseActions = new ArrayList<>();
        for (Action action : rule.getActions())
            if (action.getType())
                thenActions.add(actionConverter.convert(action, holder, policyChildren));
            else
                elseActions.add(actionConverter.convert(action, holder, policyChildren));
        RuleNode ruleNode = new RuleNode(rule.getRuleId(), listener, thenActions, elseActions);
        Condition root = rule.getRootCondition();
        if (root.getOperator() == null)
            conditionConverter.convert(root, ruleNode, holder, policyChildren);
        else
            operationConverter.convert(root, ruleNode, holder, policyChildren);
        policyChildren.put(ruleNode.getIdentifier(), ruleNode);
        return ruleNode;
    }
}
