package com.netcracker.smarthome.business.policy.core.nodes;

import com.netcracker.smarthome.ApplicationContextHolder;
import com.netcracker.smarthome.business.policy.conditions.PolicyCondition;
import com.netcracker.smarthome.business.policy.core.ExecutionManager;
import com.netcracker.smarthome.business.policy.core.listeners.ConditionCompletionListener;
import com.netcracker.smarthome.business.policy.core.nodes.ifaces.CancellableNode;
import com.netcracker.smarthome.business.policy.core.nodes.ifaces.RunnableNode;
import com.netcracker.smarthome.business.policy.events.PolicyEvent;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConditionNode implements RunnableNode, CancellableNode {
    private static final Logger log = LoggerFactory.getLogger(ConditionNode.class);
    private final ExecutionManager executionManager;
    private long identifier;
    private PolicyCondition condition;
    private ConditionCompletionListener completionListener;

    private PolicyEvent event;
    private volatile boolean cancelled;

    public ConditionNode(long identifier, PolicyCondition condition, ConditionCompletionListener completionListener) {
        this.identifier = identifier;
        this.condition = condition;
        this.completionListener = completionListener;
        this.executionManager = ApplicationContextHolder.getApplicationContext().getBean(ExecutionManager.class);
    }

    public void execute(PolicyEvent event) {
        this.event = event;
        cancelled = false;
        executionManager.execute(this);
    }

    @Override
    public void run() {
        Boolean result = null;
        if (!cancelled) {
            result = condition.evaluate(event);
        }
        completionListener.onConditionComplete(identifier, result, event);
    }

    @Override
    public void cancel() {
        cancelled = true;
    }

    @Override
    public long getIdentifier() {
        return identifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConditionNode)) return false;
        ConditionNode that = (ConditionNode) o;
        return new EqualsBuilder()
                .append(getIdentifier(), that.getIdentifier())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getIdentifier())
                .toHashCode();
    }
}
