package com.netcracker.smarthome.business.policy.transform.core;

import com.netcracker.smarthome.business.policy.core.listeners.ConditionCompletionListener;
import com.netcracker.smarthome.business.policy.core.nodes.OperationNode;
import com.netcracker.smarthome.business.policy.core.nodes.ifaces.EngineNode;
import com.netcracker.smarthome.business.policy.core.nodes.ifaces.PolicyNodesHolder;
import com.netcracker.smarthome.model.entities.Condition;

import java.util.Map;
import java.util.stream.Collectors;

class OperationConverter {
    private ConditionConverter conditionConverter;

    OperationConverter() {
        this.conditionConverter = new ConditionConverter();
    }

    public OperationNode convert(Condition operation, ConditionCompletionListener listener, PolicyNodesHolder holder, Map<Long, EngineNode> policyChildren) {
        OperationNode operationNode = new OperationNode(
                operation.getNodeId(),
                operation.getOperator(),
                listener,
                operation.getChildNodes()
                        .stream()
                        .map(Condition::getNodeId)
                        .collect(Collectors.toSet()),
                holder);
        for (Condition node : operation.getChildNodes())
            if (node.getOperator() == null)
                conditionConverter.convert(node, operationNode, holder, policyChildren);
            else
                convert(node, operationNode, holder, policyChildren);
        policyChildren.put(operationNode.getIdentifier(), operationNode);
        return operationNode;
    }
}