package com.netcracker.smarthome.business.chart.tree.obj.wrapper;


import com.netcracker.smarthome.model.entities.SmartObject;
import org.primefaces.model.TreeNode;

public class SmartObjectWrapper {
    private TreeNode parent;
    private SmartObject smartObject;

    public SmartObjectWrapper(SmartObject smartObject, TreeNode treeNode) {
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
    public String toString() {
        return smartObject.getName();
    }
}
