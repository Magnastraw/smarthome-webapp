package com.netcracker.smarthome.business.policy.transform;

import com.netcracker.smarthome.business.policy.exec.listeners.ConditionExecutionCompleteListener;
import com.netcracker.smarthome.business.policy.exec.nodes.ConditionNode;
import com.netcracker.smarthome.model.entities.Condition;
import com.netcracker.smarthome.model.entities.ConditionParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class ConditionConverter {
    private static final Logger log = LoggerFactory.getLogger(ConditionConverter.class);

    public ConditionNode convert(Condition condition, ConditionExecutionCompleteListener listener) {
        ConditionNode conditionNode = new ConditionNode(condition.getNodeId(), instantiateConditionClass(condition));
        conditionNode.setListener(listener);
        return conditionNode;
    }

    private com.netcracker.smarthome.business.policy.conditions.Condition instantiateConditionClass(Condition condition) {
        HashMap<String, String> params = new HashMap<String, String>();
        for (ConditionParam param : condition.getConditionParams()
                ) {
            params.put(param.getName(), param.getValue());
        }
        com.netcracker.smarthome.business.policy.conditions.Condition conditionClassInst = null;
        try {
            Constructor constructor = Class.forName(condition.getConditionType().getConditionClass()).getConstructor(HashMap.class);
            conditionClassInst = (com.netcracker.smarthome.business.policy.conditions.Condition) constructor.newInstance(params);
        } catch (NoSuchMethodException e) {
            log.error("Error during instantiate condition class", e);
        } catch (ClassNotFoundException e) {
            log.error("Error during instantiate condition class", e);
        } catch (IllegalAccessException e) {
            log.error("Error during instantiate condition class", e);
        } catch (InstantiationException e) {
            log.error("Error during instantiate condition class", e);
        } catch (InvocationTargetException e) {
            log.error("Error during instantiate condition class", e);
        }
        return conditionClassInst;
    }
}
