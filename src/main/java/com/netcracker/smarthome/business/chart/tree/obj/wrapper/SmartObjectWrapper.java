package com.netcracker.smarthome.business.chart.tree.obj.wrapper;


import com.netcracker.smarthome.model.entities.SmartObject;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.primefaces.model.TreeNode;

public class SmartObjectWrapper {
    private TreeNode parent;
    private SmartObject smartObject;

    public SmartObjectWrapper(TreeNode treeNode, SmartObject smartObject) {
        this.smartObject = smartObject;
        this.parent = treeNode;
    }

    public SmartObject getSmartObject() {
        return smartObject;
    }

    public void setSmartObject(SmartObject smartObject) {
        this.smartObject = smartObject;
    }

    public TreeNode getParent() {
        return parent;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        SmartObjectWrapper that = (SmartObjectWrapper) o;

        return new EqualsBuilder()
                .append(smartObject, that.smartObject)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(smartObject)
                .toHashCode();
    }

    @Override
    public String toString() {
        return smartObject.getName();
    }
}
