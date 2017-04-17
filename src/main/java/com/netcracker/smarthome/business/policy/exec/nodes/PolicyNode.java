package com.netcracker.smarthome.business.policy.exec.nodes;

import com.netcracker.smarthome.business.policy.events.Event;
import com.netcracker.smarthome.business.policy.exec.listeners.RuleExecutionCompleteListener;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;
import java.util.Queue;

//TODO: change when multithreading will be added : add synchronization
public class PolicyNode implements RuleExecutionCompleteListener {
    private long identifier;
    private boolean locked;
    private int rulesCount, incompleteRulesCount;
    private List<ConditionNode> leafConditions;

    private Queue<Event> eventQueue;

    public PolicyNode(long identifier, int rulesCount) {
        this.identifier = identifier;
        this.rulesCount = rulesCount;
    }

    public void onRuleExecComplete(long ruleId) {
        incompleteRulesCount--;
        if (eventHandleComplete()) {
            unlock();
            handleNextEvent();
        }
    }

    public void handle(Event event) {
        eventQueue.add(event);
        if (!locked)
            handleNextEvent();
    }

    private void handleNextEvent() {
        if (!eventQueue.isEmpty()) {
            locked = true;
            runConditions(eventQueue.poll());
        }
    }

    private void runConditions(Event event) {
        for (ConditionNode condition : leafConditions
                ) {
            condition.execute(event);
        }
    }

    private boolean eventHandleComplete() {
        return incompleteRulesCount > 0;
    }

    public long getIdentifier() {
        return identifier;
    }

    private void unlock() {
        locked = false;
        reset();
    }

    private void reset() {
        incompleteRulesCount = rulesCount;
    }

    public void setLeafConditions(List<ConditionNode> leafConditions) {
        if (this.leafConditions == null)
            this.leafConditions = leafConditions;
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
