package com.netcracker.smarthome.business.policy.transform.json;

import com.netcracker.smarthome.business.endpoints.transformators.ITransformator;
import com.netcracker.smarthome.business.policy.transform.json.entities.JsonRule;
import com.netcracker.smarthome.model.entities.Rule;

import java.util.stream.Collectors;

public class RuleTransformer implements ITransformator<Rule, JsonRule> {
    private final ConditionTransformer conditionTransformer;
    private final ActionTransformer actionTransformer;

    public RuleTransformer() {
        this.conditionTransformer = new ConditionTransformer();
        this.actionTransformer = new ActionTransformer();
    }

    @Override
    public Rule fromJsonEntity(JsonRule jsonRule) {
        throw new UnsupportedOperationException("Conversion from JSON is not supported!");
    }

    @Override
    public JsonRule toJsonEntity(Rule rule) {
        JsonRule jsonRule = new JsonRule();
        jsonRule.setId(rule.getRuleId());
        jsonRule.setName(rule.getName());
        if (rule.getRootCondition() != null)
            jsonRule.setRootCondition(conditionTransformer.toJsonEntity(rule.getRootCondition()));
        jsonRule.setThenActions(rule.getActions()
                .stream()
                .filter(action -> action.getType())
                .map(actionTransformer::toJsonEntity)
                .collect(Collectors.toList())
        );
        jsonRule.setElseActions(rule.getActions()
                .stream()
                .filter(action -> !action.getType())
                .map(actionTransformer::toJsonEntity)
                .collect(Collectors.toList())
        );
        return jsonRule;
    }
}
