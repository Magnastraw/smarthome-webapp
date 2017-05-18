package com.netcracker.smarthome.web.policy.components;

import com.netcracker.smarthome.model.enums.BooleanOperator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UIOperator implements UIConditionTreeNode {
    private List<UIConditionTreeNode> children;
    private Map<String, UIParameter> templateParams;

    public UIOperator(BooleanOperator operator, List<UIConditionTreeNode> children) {
        this.children = children;
        this.templateParams = new HashMap<>();
        templateParams.put("@operator", new UIParameter(operator.toString()));
    }

    public List<UIConditionTreeNode> getChildren() {
        return children;
    }

    @Override
    public String getTemplate() {
        return "@operator";
    }

    @Override
    public Map<String, UIParameter> getTemplateParameters() {
        return templateParams;
    }
}
