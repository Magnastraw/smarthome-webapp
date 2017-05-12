package com.netcracker.smarthome.business.policy.core.nodes;

import com.netcracker.smarthome.ApplicationContextHolder;
import com.netcracker.smarthome.business.policy.core.ExecutionManager;
import com.netcracker.smarthome.business.policy.core.listeners.ConditionCompletionListener;
import com.netcracker.smarthome.business.policy.core.listeners.RuleCompletionListener;
import com.netcracker.smarthome.business.policy.core.nodes.ifaces.RunnableNode;
import com.netcracker.smarthome.business.policy.events.PolicyEvent;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class RuleNode implements ConditionCompletionListener, RunnableNode {
    private static final Logger log = LoggerFactory.getLogger(RuleNode.class);
    private final ExecutionManager executionManager;
    private long identifier;
    private List<ActionNode> thenActions, elseActions;
    private RuleCompletionListener completionListener;

    private List<ActionNode> actionsToExecute;
    private PolicyEvent event;

    public RuleNode(long identifier, RuleCompletionListener completionListener, List<ActionNode> thenActions, List<ActionNode> elseActions) {
        this.identifier = identifier;
        this.completionListener = completionListener;
        this.thenActions = thenActions;
        this.elseActions = elseActions;
        this.executionManager = ApplicationContextHolder.getApplicationContext().getBean(ExecutionManager.class);
    }

    public void onConditionComplete(long conditionId, Boolean result, PolicyEvent event) {
        if (result != null) {
            actionsToExecute = result ? thenActions : elseActions;
            this.event = event;
            log.info("Rule #{} execute. Condition result: {}, actions to execute: {}", identifier, result, actionsToExecute.size());
            executionManager.execute(this);
        }
        else
            complete();
    }

    @Override
    public void run() {
        for (ActionNode action : actionsToExecute)
            action.execute(event);
        complete();
    }

    private void complete() {
        actionsToExecute = null;
        event = null;
        completionListener.onRuleComplete(identifier);
    }

    @Override
    public long getIdentifier() {
        return identifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RuleNode)) return false;
        RuleNode ruleNode = (RuleNode) o;
        return new EqualsBuilder()
                .append(identifier, ruleNode.identifier)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(identifier)
                .toHashCode();
    }
}
