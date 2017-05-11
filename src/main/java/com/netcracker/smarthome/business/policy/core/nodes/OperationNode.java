package com.netcracker.smarthome.business.policy.core.nodes;

import com.netcracker.smarthome.business.policy.core.listeners.ConditionCompletionListener;
import com.netcracker.smarthome.business.policy.core.nodes.ifaces.CancellableNode;
import com.netcracker.smarthome.business.policy.core.nodes.ifaces.PolicyNodesHolder;
import com.netcracker.smarthome.business.policy.events.PolicyEvent;
import com.netcracker.smarthome.model.enums.BooleanOperator;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class OperationNode implements ConditionCompletionListener, CancellableNode {
    private static final Logger log = LoggerFactory.getLogger(OperationNode.class);
    private long identifier;
    private BooleanOperator operator;
    private ConditionCompletionListener completionListener;
    private PolicyNodesHolder nodesHolder;

    private Map<Long, Boolean> childrenStatuses;
    private PolicyEvent event;
    private Boolean nodeResult;
    private int incompleteChildrenCount;
    private volatile boolean cancelled;

    public OperationNode(long identifier, BooleanOperator operator, ConditionCompletionListener completionListener, Collection<Long> childNodes, PolicyNodesHolder nodesHolder) {
        this.identifier = identifier;
        this.operator = operator;
        this.childrenStatuses = new ConcurrentHashMap<>();
        this.nodesHolder = nodesHolder;
        this.incompleteChildrenCount = childNodes.size();
        this.completionListener = completionListener;
        for (Long node : childNodes)
            childrenStatuses.put(node, false);
    }

    public void onConditionComplete(long conditionId, Boolean result, PolicyEvent event) {
        synchronized (this) {
            childrenStatuses.put(conditionId, true);
            incompleteChildrenCount--;
            if (!cancelled && result != null) {
                if (nodeResult == null) {
                    nodeResult = result;
                    this.event = event;
                } else
                    nodeResult = operator.evaluate(result, nodeResult);
                if (nodeResult.equals(operator.completeValue()))
                    interrupt();
            }
            if (incompleteChildrenCount == 0)
                complete();
        }
    }

    private void interrupt() {
        if (incompleteChildrenCount > 0) {
            cancelled = true;
            cancelChildren();
        }
    }

    private void cancelChildren() {
        for (Long key : childrenStatuses.keySet())
            if (!childrenStatuses.get(key))
                ((CancellableNode) nodesHolder.getNode(key)).cancel();
    }

    private void complete() {
        completionListener.onConditionComplete(identifier, nodeResult, event);
        reset();
    }

    private void reset() {
        for (Long node : childrenStatuses.keySet())
            childrenStatuses.put(node, false);
        incompleteChildrenCount = childrenStatuses.keySet().size();
        nodeResult = null;
        cancelled = false;
        event = null;
    }

    @Override
    public void cancel() {
        synchronized (this) {
            interrupt();
        }
    }

    @Override
    public long getIdentifier() {
        return identifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OperationNode)) return false;
        OperationNode that = (OperationNode) o;
        return new EqualsBuilder()
                .append(identifier, that.identifier)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(identifier)
                .toHashCode();
    }
}
