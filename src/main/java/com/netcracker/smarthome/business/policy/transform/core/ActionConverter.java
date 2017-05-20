package com.netcracker.smarthome.business.policy.transform.core;

import com.netcracker.smarthome.business.policy.actions.PolicyAction;
import com.netcracker.smarthome.business.policy.core.nodes.ActionNode;
import com.netcracker.smarthome.business.policy.core.nodes.ifaces.EngineNode;
import com.netcracker.smarthome.business.policy.core.nodes.ifaces.PolicyNodesHolder;
import com.netcracker.smarthome.model.entities.Action;
import com.netcracker.smarthome.model.entities.ActionParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.stream.Collectors;

class ActionConverter {
    private static final Logger log = LoggerFactory.getLogger(ActionConverter.class);

    public ActionNode convert(Action sourceObject, PolicyNodesHolder holder, Map<Long, EngineNode> policyChildren) {
        ActionNode actionNode = new ActionNode(sourceObject.getActionId(), instantiateActionClass(sourceObject));
        policyChildren.put(actionNode.getIdentifier(), actionNode);
        return actionNode;
    }

    private PolicyAction instantiateActionClass(Action action) {
        Map<String, String> params = action.getActionParams()
                .stream()
                .collect(Collectors.toMap(ActionParam::getName, ActionParam::getValue));
        PolicyAction actionClassInst = null;
        try {
            Constructor constructor = Class.forName(action.getCoreClass().getName()).getConstructor(Map.class);
            actionClassInst = (PolicyAction) constructor.newInstance(params);
        } catch (Exception e) {
            log.error("Error during instantiate action class", e);
        }
        return actionClassInst;
    }
}
