package com.netcracker.smarthome.business.policy.transform.json;

import com.netcracker.smarthome.business.endpoints.transformators.ITransformator;
import com.netcracker.smarthome.business.policy.transform.json.entities.JsonCondition;
import com.netcracker.smarthome.business.policy.transform.json.entities.JsonParams;
import com.netcracker.smarthome.model.entities.Condition;
import com.netcracker.smarthome.model.entities.ConditionParam;

import java.util.stream.Collectors;

public class ConditionTransformer implements ITransformator<Condition, JsonCondition> {
    @Override
    public Condition fromJsonEntity(JsonCondition jsonCondition) {
        throw new UnsupportedOperationException("Not supported operation!");
    }

    @Override
    public JsonCondition toJsonEntity(Condition condition) {
        JsonCondition jsonCondition = new JsonCondition();
        jsonCondition.setId(condition.getNodeId());
        jsonCondition.setOperator(condition.getOperator());
        jsonCondition.setConditionClass(condition.getConditionType().getConditionClass());
        jsonCondition.setParams(new JsonParams(condition.getConditionParams()
                        .stream()
                        .collect(Collectors.toMap(ConditionParam::getName, ConditionParam::getValue)))
        );
        jsonCondition.setChildren(condition.getChildNodes()
                .stream()
                .map(this::toJsonEntity)
                .collect(Collectors.toList())
        );
        return jsonCondition;
    }
}
