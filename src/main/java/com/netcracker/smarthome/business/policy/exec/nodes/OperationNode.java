package com.netcracker.smarthome.business.policy.exec.nodes;

import com.netcracker.smarthome.business.policy.events.PolicyEvent;
import com.netcracker.smarthome.business.policy.exec.listeners.ConditionExecutionCompleteListener;
import com.netcracker.smarthome.model.enums.BooleanOperator;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

//TODO: change when multithreading will be added : add synchronization
public class OperationNode implements ConditionExecutionCompleteListener, ExecutionNode {
    private static final Logger log = LoggerFactory.getLogger(OperationNode.class);
    private long identifier;
    private BooleanOperator operator;
    private Set<Long> childNodes, incompleteChildNodes;
    private ConditionExecutionCompleteListener listener;

    private Boolean nodeResult;
    private PolicyEvent event;

    public OperationNode(long identifier, BooleanOperator operator, Set<Long> childNodes) {
        this.identifier = identifier;
        this.operator = operator;
        this.childNodes = childNodes;
    }

    //on child condition complete callback without thread starting
    public void onConditionExecComplete1(long conditionId, boolean result, PolicyEvent event) {
        if (nodeResult == null) {
            nodeResult = result;
            this.event = event;
        } else
            nodeResult = operator.evaluate(nodeResult, result);
        incompleteChildNodes.remove(conditionId);
        if (canComplete())
            complete();
    }

    //on child condition complete callback with thread starting
    public void onConditionExecComplete(long conditionId, boolean result, PolicyEvent event) {
        if (nodeResult == null) {
            nodeResult = result;
            this.event = event;
            log.debug("Start execution thread via ExecutorManager");
        } else
            nodeResult = operator.evaluate(nodeResult, result);
        incompleteChildNodes.remove(conditionId);
    }

    public void run() {
        while (!canComplete()) ;
        complete();
    }

    private boolean canComplete() {
        return incompleteChildNodes.isEmpty() || nodeResult.equals(operator.completeValue());
    }

    private void complete() {
        listener.onConditionExecComplete(identifier, nodeResult, event);
        if (!incompleteChildNodes.isEmpty()) {
            log.debug("Cancel incomplete child tasks");
        }
        reset();
    }

    private void reset() {
        incompleteChildNodes = new HashSet<Long>(childNodes);
        nodeResult = null;
    }


    public void setListener(ConditionExecutionCompleteListener listener) {
        if (this.listener == null)
            this.listener = listener;
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
