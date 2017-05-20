package com.netcracker.smarthome.web.policy;

import com.netcracker.smarthome.web.policy.components.UIComponent;
import com.netcracker.smarthome.web.policy.components.UIParameter;

import java.util.Map;

public class ContentBuilder {
    public Object getContent(boolean readOnlyMode, UIComponent component) {
        if (readOnlyMode) {
            String content = component.getTemplate();
            Map<String, UIParameter> templateParams = component.getTemplateParameters();
            for (String paramKey : templateParams.keySet()) {
                content = content.replaceFirst(paramKey, getWrappedText(templateParams.get(paramKey).getReadOnlyValue().toString()));
            }
            return content;
        }
        return null;
    }

    private String getWrappedText(String text) {
        if (text.length() < 25)
            return String.format("<span style=\"font-weight: bold;\"><nobr>%s</nobr></span>", text);
        else
            return String.format("<span style=\"font-weight: bold;\">%s</span>", text);
    }
}
