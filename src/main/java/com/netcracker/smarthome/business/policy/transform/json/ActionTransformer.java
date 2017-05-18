package com.netcracker.smarthome.business.policy.transform.json;

import com.netcracker.smarthome.business.endpoints.transformators.ITransformator;
import com.netcracker.smarthome.business.policy.transform.json.entities.JsonAction;
import com.netcracker.smarthome.business.policy.transform.json.entities.JsonParams;
import com.netcracker.smarthome.model.entities.Action;
import com.netcracker.smarthome.model.entities.ActionParam;

import java.util.stream.Collectors;

public class ActionTransformer implements ITransformator<Action, JsonAction> {
    @Override
    public Action fromJsonEntity(JsonAction jsonAction) {
        throw new UnsupportedOperationException("Conversion from JSON is not supported!");
    }

    @Override
    public JsonAction toJsonEntity(Action action) {
        JsonAction jsonAction = new JsonAction();
        jsonAction.setId(action.getActionId());
        jsonAction.setOrder(action.getOrder());
        jsonAction.setActionClass(action.getCoreClass().getName());
        jsonAction.setParams(new JsonParams(action.getActionParams()
                .stream()
                .collect(Collectors.toMap(ActionParam::getName, ActionParam::getValue))));
        return jsonAction;
    }
}
