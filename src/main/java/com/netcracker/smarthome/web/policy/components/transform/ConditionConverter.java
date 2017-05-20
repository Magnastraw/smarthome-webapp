package com.netcracker.smarthome.web.policy.components.transform;

import com.netcracker.smarthome.model.entities.Condition;
import com.netcracker.smarthome.model.entities.ConditionParam;
import com.netcracker.smarthome.web.policy.components.UICondition;
import com.netcracker.smarthome.web.policy.components.UIConditionTreeNode;
import com.netcracker.smarthome.web.policy.components.UIOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.stream.Collectors;

public class ConditionConverter implements Converter<Condition, UIConditionTreeNode> {
    private static final Logger log = LoggerFactory.getLogger(ConditionConverter.class);

    public UIConditionTreeNode convert(Condition condition) {
        if (condition.getOperator() == null)
            return convertCondition(condition);
        else
            return convertOperation(condition);
    }

    private UICondition convertCondition(Condition condition) {
        Map<String, String> params = condition.getConditionParams()
                .stream()
                .collect(Collectors.toMap(ConditionParam::getName, ConditionParam::getValue));
        UICondition uiCondition = null;
        try {
            Constructor constructor = Class.forName(condition.getUiClass().getName()).getConstructor(Map.class);
            uiCondition = (UICondition) constructor.newInstance(params);
        } catch (Exception e) {
            log.error("Error during instantiate condition class", e);
        }
        return uiCondition;
    }

    private UIOperator convertOperation(Condition operation) {
        return new UIOperator(
                operation.getOperator(),
                operation.getChildNodes()
                        .stream()
                        .map(this::convert)
                        .collect(Collectors.toList())
        );
    }
}
