package com.netcracker.smarthome.web.policy.components;

public class UIParameter {
    private Object readOnlyValue;
    private Object editValue;

    public UIParameter(Object readOnlyValue) {
        this.readOnlyValue = readOnlyValue;
    }

    public Object getReadOnlyValue() {
        return readOnlyValue;
    }

    public void setReadOnlyValue(Object readOnlyValue) {
        this.readOnlyValue = readOnlyValue;
    }

    public Object getEditValue() {
        return editValue;
    }

    public void setEditValue(Object editValue) {
        this.editValue = editValue;
    }
}
