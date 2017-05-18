package com.netcracker.smarthome.business.policy.transform.core;

import com.netcracker.smarthome.business.policy.conditions.PolicyCondition;
import com.netcracker.smarthome.business.policy.core.listeners.ConditionCompletionListener;
import com.netcracker.smarthome.business.policy.core.nodes.ConditionNode;
import com.netcracker.smarthome.business.policy.core.nodes.ifaces.EngineNode;
import com.netcracker.smarthome.business.policy.core.nodes.ifaces.PolicyNodesHolder;
import com.netcracker.smarthome.model.entities.Condition;
import com.netcracker.smarthome.model.entities.ConditionParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.stream.Collectors;

class ConditionConverter {
    private static final Logger log = LoggerFactory.getLogger(ConditionConverter.class);

    public ConditionNode convert(Condition condition, ConditionCompletionListener listener, PolicyNodesHolder holder, Map<Long, EngineNode> policyChildren) {
        ConditionNode conditionNode = new ConditionNode(condition.getNodeId(), instantiateConditionClass(condition), listener);
        policyChildren.put(conditionNode.getIdentifier(), conditionNode);
        return conditionNode;
    }

    private PolicyCondition instantiateConditionClass(Condition condition) {
        Map<String, String> params = condition.getConditionParams()
                .stream()
                .collect(Collectors.toMap(ConditionParam::getName, ConditionParam::getValue));
        PolicyCondition conditionClassInst = null;
        try {
            Constructor constructor = Class.forName(condition.getCoreClass().getName()).getConstructor(Map.class);
            conditionClassInst = (PolicyCondition) constructor.newInstance(params);
        } catch (Exception e) {
            log.error("Error during instantiate condition class", e);
        }
        return conditionClassInst;
    }
}
