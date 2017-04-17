package com.netcracker.smarthome.business.policy.transform;

import com.netcracker.smarthome.business.policy.exec.listeners.ActionExecutionCompleteListener;
import com.netcracker.smarthome.business.policy.exec.nodes.ActionNode;
import com.netcracker.smarthome.model.entities.Action;
import com.netcracker.smarthome.model.entities.ActionParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class ActionConverter {
    private static final Logger log = LoggerFactory.getLogger(ActionConverter.class);

    public ActionNode convert(Action sourceObject, ActionExecutionCompleteListener listener) {
        return new ActionNode(sourceObject.getActionId(), instantiateActionClass(sourceObject), listener);
    }

    private com.netcracker.smarthome.business.policy.actions.Action instantiateActionClass(Action action) {
        HashMap<String, String> params = new HashMap<String, String>();
        for (ActionParam param : action.getActionParams()
                ) {
            params.put(param.getName(), param.getValue());
        }
        com.netcracker.smarthome.business.policy.actions.Action actionClassInst = null;
        try {
            Constructor constructor = Class.forName(action.getActionClass()).getConstructor(HashMap.class);
            actionClassInst = (com.netcracker.smarthome.business.policy.actions.Action) constructor.newInstance(params);
        } catch (NoSuchMethodException e) {
            log.error("Error during instantiate action class", e);
        } catch (ClassNotFoundException e) {
            log.error("Error during instantiate action class", e);
        } catch (IllegalAccessException e) {
            log.error("Error during instantiate action class", e);
        } catch (InstantiationException e) {
            log.error("Error during instantiate action class", e);
        } catch (InvocationTargetException e) {
            log.error("Error during instantiate action class", e);
        }
        return actionClassInst;
    }
}
