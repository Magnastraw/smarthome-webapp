package com.netcracker.smarthome.business.policy.core.nodes;

import com.netcracker.smarthome.business.policy.core.listeners.RuleCompletionListener;
import com.netcracker.smarthome.business.policy.core.nodes.ifaces.EngineNode;
import com.netcracker.smarthome.business.policy.core.nodes.ifaces.PolicyNodesHolder;
import com.netcracker.smarthome.business.policy.events.PolicyEvent;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class PolicyNode implements RuleCompletionListener, PolicyNodesHolder, EngineNode {
    private long identifier;
    private int rulesCount;
    private List<ConditionNode> leafConditions;
    private Map<Long, EngineNode> children;

    private volatile boolean locked;
    private int incompleteRulesCount;
    private Queue<PolicyEvent> eventQueue;

    public PolicyNode(long identifier, int rulesCount) {
        this.identifier = identifier;
        this.rulesCount = rulesCount;
        this.eventQueue = new ConcurrentLinkedQueue<>();
    }

    public synchronized void onRuleComplete(long ruleId) {
        incompleteRulesCount--;
        if (incompleteRulesCount == 0) {
            unlock();
            handleNextEvent();
        }
    }

    public void handle(PolicyEvent event) {
        eventQueue.add(event);
        if (!locked)
            handleNextEvent();
    }

    private synchronized void handleNextEvent() {
        if (!eventQueue.isEmpty()) {
            locked = true;
            launchConditions(eventQueue.poll());
        }
    }

    private void launchConditions(PolicyEvent event) {
        for (ConditionNode condition : leafConditions)
            condition.execute(event);
    }

    private void unlock() {
        locked = false;
        reset();
    }

    private void reset() {
        incompleteRulesCount = rulesCount;
    }

    public void setChildren(Map<Long, EngineNode> children) {
        if (this.children != null) {
            this.children = children;
            leafConditions = new ArrayList<>();
            for (EngineNode executionNode : children.values())
                if (executionNode instanceof ConditionNode)
                    leafConditions.add((ConditionNode) executionNode);

        }
    }

    @Override
    public long getIdentifier() {
        return identifier;
    }

    @Override
    public EngineNode getNode(long identifier) {
        return children.get(identifier);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PolicyNode)) return false;
        PolicyNode that = (PolicyNode) o;
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
