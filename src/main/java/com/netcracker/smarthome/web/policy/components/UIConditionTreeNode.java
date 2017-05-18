package com.netcracker.smarthome.web.policy.components;

import java.util.Map;

public interface UIConditionTreeNode {
    String getTemplate();
    Map<String, UIParameter> getTemplateParameters();
}
