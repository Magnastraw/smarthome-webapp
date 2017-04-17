package com.netcracker.smarthome.business.policy.transform;

import com.netcracker.smarthome.business.policy.exec.listeners.ConditionExecutionCompleteListener;
import com.netcracker.smarthome.business.policy.exec.nodes.OperationNode;
import com.netcracker.smarthome.model.entities.Condition;

import java.util.HashSet;
import java.util.Set;

public class OperationConverter {
    private ConditionConverter conditionConverter = new ConditionConverter();

    public OperationNode convert(Condition operation, ConditionExecutionCompleteListener listener) {
        OperationNode operationNode = new OperationNode(operation.getNodeId(), operation.getOperator(), getChildNodesIds(operation.getChildNodes()));
        operationNode.setListener(listener);
        for (Condition node : operation.getChildNodes()
                ) {
            if (node.getOperator() == null)
                conditionConverter.convert(node, operationNode);
            else
                convert(node, operationNode);
        }
        return operationNode;
    }

    private Set<Long> getChildNodesIds(Set<Condition> children) {
        Set<Long> ids = new HashSet<Long>();
        for (Condition child : children
                ) {
            ids.add(child.getNodeId());
        }
        return ids;
    }
}