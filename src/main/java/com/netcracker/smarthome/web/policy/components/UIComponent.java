package com.netcracker.smarthome.web.policy.components;

import java.util.Map;

public interface UIComponent {
    String getTemplate();
    Map<String, UIParameter> getTemplateParameters();
}
