package com.netcracker.smarthome.business.policy.core.nodes;

import com.netcracker.smarthome.business.policy.actions.PolicyAction;
import com.netcracker.smarthome.business.policy.events.PolicyEvent;
import com.netcracker.smarthome.business.policy.core.nodes.ifaces.EngineNode;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActionNode implements EngineNode {
    private static final Logger log = LoggerFactory.getLogger(ActionNode.class);
    private long identifier;
    private PolicyAction action;

    public ActionNode(long identifier, PolicyAction action) {
        this.identifier = identifier;
        this.action = action;
    }

    public void execute(PolicyEvent event) {
        action.execute(event);
    }

    @Override
    public long getIdentifier() {
        return identifier;
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
