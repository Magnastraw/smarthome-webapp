package com.netcracker.smarthome.business.policy.exec.nodes;

import com.netcracker.smarthome.business.policy.actions.Action;
import com.netcracker.smarthome.business.policy.events.Event;
import com.netcracker.smarthome.business.policy.exec.listeners.ActionExecutionCompleteListener;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActionNode implements ExecutionNode {
    private static final Logger log = LoggerFactory.getLogger(ActionNode.class);
    private long identifier;
    private Action action;
    private ActionExecutionCompleteListener listener;

    private Event event;

    public ActionNode(long identifier, Action action, ActionExecutionCompleteListener listener) {
        this.identifier = identifier;
        this.action = action;
        this.listener = listener;
    }

    public void execute(Event event) {
        this.event = event;
        log.debug("Start execution thread via ExecutorManager");
    }

    public void run() {
        action.execute(event);
        listener.onActionExecComplete(identifier);
    }

    public long getIdentifier() {
        return identifier;
    }

    public void setListener(ActionExecutionCompleteListener listener) {
        if (this.listener == null)
            this.listener = listener;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof ActionNode)) return false;

        ActionNode that = (ActionNode) o;

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
