package com.netcracker.smarthome.web.policy.components.transform;

import com.netcracker.smarthome.model.entities.Action;
import com.netcracker.smarthome.model.entities.Rule;
import com.netcracker.smarthome.web.policy.components.UIAction;
import com.netcracker.smarthome.web.policy.components.UIRule;

import java.util.ArrayList;
import java.util.List;

public class RuleConverter implements Converter<Rule, UIRule> {
    private ActionConverter actionConverter;
    private ConditionConverter conditionConverter;

    public RuleConverter() {
        this.actionConverter = new ActionConverter();
        this.conditionConverter = new ConditionConverter();
    }

    public UIRule convert(Rule rule) {
        List<UIAction> thenActions = new ArrayList<>(), elseActions = new ArrayList<>();
        for (Action action : rule.getActions())
            if (action.getType())
                thenActions.add(actionConverter.convert(action));
            else
                elseActions.add(actionConverter.convert(action));
        return new UIRule(
                rule.getName(),
                rule.getRootCondition() == null ? null : conditionConverter.convert(rule.getRootCondition()),
                thenActions,
                elseActions
        );
    }
}
