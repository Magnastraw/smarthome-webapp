package com.netcracker.smarthome.web.policy.jsf;

import com.netcracker.smarthome.web.policy.ContentBuilder;
import com.netcracker.smarthome.web.policy.components.UIComponent;
import org.primefaces.model.DefaultOrganigramNode;
import org.primefaces.model.OrganigramNode;


public class ReadOnlyOrganigramNode extends DefaultOrganigramNode {
    private ContentBuilder contentBuilder;

    public ReadOnlyOrganigramNode(String type, Object data, OrganigramNode parent) {
        super(type, data, parent);
        setSelectable(true);
        contentBuilder = new ContentBuilder();
    }

    public Object getText() {
        UIComponent component = (UIComponent) super.getData();
        return contentBuilder.getContent(true, component);
    }
}
