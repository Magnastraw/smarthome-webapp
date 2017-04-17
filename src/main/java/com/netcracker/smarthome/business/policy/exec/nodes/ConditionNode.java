package com.netcracker.smarthome.business.policy.exec.nodes;

import com.netcracker.smarthome.business.policy.conditions.Condition;
import com.netcracker.smarthome.business.policy.events.PolicyEvent;
import com.netcracker.smarthome.business.policy.exec.listeners.ConditionExecutionCompleteListener;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConditionNode implements ExecutionNode {
    private static final Logger log = LoggerFactory.getLogger(ConditionNode.class);
    private long identifier;
    private Condition condition;
    private ConditionExecutionCompleteListener listener;

    private PolicyEvent event;

    public ConditionNode(long identifier, Condition condition) {
        this.identifier = identifier;
        this.condition = condition;
    }

    public void execute(PolicyEvent event) {
        this.event = event;
        log.debug("Start execution thread via ExecutorManager");
    }

    public void run() {
        boolean result = condition.evaluate(event);
        listener.onConditionExecComplete(identifier, result, event);
    }

    public long getIdentifier() {
        return identifier;
    }

    public void setListener(ConditionExecutionCompleteListener listener) {
        if (this.listener == null)
            this.listener = listener;
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
