package com.netcracker.smarthome.web.policy.components.conditions;

import com.netcracker.smarthome.ApplicationContextHolder;
import com.netcracker.smarthome.dal.repositories.SmartObjectRepository;
import com.netcracker.smarthome.model.entities.SmartObject;
import com.netcracker.smarthome.web.policy.components.UIParameter;

import java.util.Map;

public class InlineStrictRangeCondition extends StrictRangeCondition {
    public InlineStrictRangeCondition(Map<String, String> params) {
        super(params);
    }

    @Override
    protected void initParamsMap(Map<String, String> params) {
        super.initParamsMap(params);
        SmartObjectRepository objectRepository = ApplicationContextHolder.getApplicationContext().getBean(SmartObjectRepository.class);
        SmartObject object = objectRepository.get(Long.parseLong(params.get("object")));
        templateParams.put("@object", new UIParameter(object == null ? "" : object.getName()));
    }

    @Override
    public String getTemplate() {
        return "@min < @metric on object @object < @max";
    }
}
