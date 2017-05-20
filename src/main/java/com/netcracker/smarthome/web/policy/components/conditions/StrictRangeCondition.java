package com.netcracker.smarthome.web.policy.components.conditions;

import com.netcracker.smarthome.web.policy.components.UIParameter;

import java.util.Map;

public class StrictRangeCondition extends MetricCondition {
    public StrictRangeCondition(Map<String, String> params) {
        super(params);
    }

    @Override
    protected void initParamsMap(Map<String, String> params) {
        super.initParamsMap(params);
        templateParams.put("@min", new UIParameter(Double.parseDouble(params.get("minValue"))));
        templateParams.put("@max", new UIParameter(Double.parseDouble(params.get("maxValue"))));
    }

    @Override
    public String getTemplate() {
        return "@min < @metric < @max";
    }
}
