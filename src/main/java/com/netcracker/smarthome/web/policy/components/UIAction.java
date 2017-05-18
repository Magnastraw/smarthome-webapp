package com.netcracker.smarthome.web.policy.components;

import java.util.Map;

public interface UIAction {
    String getTemplate();
    Map<String, UIParameter> getTemplateParameters();
}
