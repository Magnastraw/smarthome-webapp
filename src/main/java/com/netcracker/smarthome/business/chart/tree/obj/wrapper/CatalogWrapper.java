package com.netcracker.smarthome.business.chart.tree.obj.wrapper;


import com.netcracker.smarthome.model.entities.Catalog;
import org.primefaces.model.TreeNode;

public class CatalogWrapper {
    private TreeNode parent;
    private Catalog catalog;

    public CatalogWrapper(TreeNode parent, Catalog catalog) {
        this.parent = parent;
        this.catalog = catalog;
    }

    public TreeNode getParent() {
        return parent;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    @Override
    public String toString() {
        return catalog.getCatalogName();
    }
}
