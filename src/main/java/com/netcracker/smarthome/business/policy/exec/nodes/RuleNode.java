package com.netcracker.smarthome.business.policy.exec.nodes;

import com.netcracker.smarthome.business.policy.events.PolicyEvent;
import com.netcracker.smarthome.business.policy.exec.listeners.ActionExecutionCompleteListener;
import com.netcracker.smarthome.business.policy.exec.listeners.ConditionExecutionCompleteListener;
import com.netcracker.smarthome.business.policy.exec.listeners.RuleExecutionCompleteListener;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

//TODO: change when multithreading will be added : add synchronization
public class RuleNode implements ConditionExecutionCompleteListener, ActionExecutionCompleteListener {
    private static final Logger log = LoggerFactory.getLogger(RuleNode.class);
    private long identifier;
    private List<ActionNode> thenActions, elseActions;
    private RuleExecutionCompleteListener listener;

    private int incompleteActionsCount;

    public RuleNode(long identifier) {
        this.identifier = identifier;
    }

    public void onConditionExecComplete(long conditionId, boolean result, PolicyEvent event) {
        incompleteActionsCount = result ? thenActions.size() : elseActions.size();
        if (canComplete())
            complete();
        else
            runActions(result ? thenActions : elseActions, event);
    }

    public void onActionExecComplete(long actionId) {
        incompleteActionsCount--;
        if (canComplete())
            complete();
    }

    private void runActions(List<ActionNode> actions, PolicyEvent event) {
        for (ActionNode action : actions
                ) {
            action.execute(event);
        }
    }

    private boolean canComplete() {
        return incompleteActionsCount > 0;
    }

    private void complete() {
        listener.onRuleExecComplete(identifier);
    }

    public void setThenActions(List<ActionNode> thenActions) {
        if (this.thenActions == null)
            this.thenActions = thenActions;
    }

    public void setElseActions(List<ActionNode> elseActions) {
        if (this.elseActions == null)
            this.elseActions = elseActions;
    }

    public void setListener(RuleExecutionCompleteListener listener) {
        if (this.listener == null)
            this.listener = listener;
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
