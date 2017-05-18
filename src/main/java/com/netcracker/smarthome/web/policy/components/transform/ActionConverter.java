package com.netcracker.smarthome.web.policy.components.transform;

import com.netcracker.smarthome.model.entities.Action;
import com.netcracker.smarthome.model.entities.ActionParam;
import com.netcracker.smarthome.web.policy.components.UIAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.stream.Collectors;

public class ActionConverter implements Converter<Action, UIAction> {
    private static final Logger log = LoggerFactory.getLogger(ActionConverter.class);

    public UIAction convert(Action action) {
        Map<String, String> params = action.getActionParams()
                .stream()
                .collect(Collectors.toMap(ActionParam::getName, ActionParam::getValue));
        UIAction uiAction = null;
        try {
            Constructor constructor = Class.forName(action.getUiClass().getName()).getConstructor(Map.class);
            uiAction = (UIAction) constructor.newInstance(params);
        } catch (Exception e) {
            log.error("Error during instantiate action class", e);
        }
        return uiAction;
    }
}
