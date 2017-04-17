package com.netcracker.smarthome.business.policy.transform;

import com.netcracker.smarthome.business.policy.exec.listeners.RuleExecutionCompleteListener;
import com.netcracker.smarthome.business.policy.exec.nodes.ActionNode;
import com.netcracker.smarthome.business.policy.exec.nodes.RuleNode;
import com.netcracker.smarthome.model.entities.Action;
import com.netcracker.smarthome.model.entities.Rule;

import java.util.LinkedList;
import java.util.List;

public class RuleConverter {
    private ActionConverter actionConverter = new ActionConverter();

    public RuleNode convert(Rule rule, RuleExecutionCompleteListener listener) {
        RuleNode ruleNode = new RuleNode(rule.getRuleId());
        ruleNode.setListener(listener);
        List<ActionNode> thenActions = new LinkedList<ActionNode>(), elseActions = new LinkedList<ActionNode>();
        for (Action action : rule.getActions()
                ) {
            if (action.getType())
                thenActions.add(actionConverter.convert(action, ruleNode));
            else
                elseActions.add(actionConverter.convert(action, ruleNode));
        }
        return ruleNode;
    }
}
