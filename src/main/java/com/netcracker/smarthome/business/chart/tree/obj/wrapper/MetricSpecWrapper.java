package com.netcracker.smarthome.business.chart.tree.obj.wrapper;


import com.netcracker.smarthome.model.entities.MetricSpec;
import org.primefaces.model.TreeNode;

public class MetricSpecWrapper {
    private TreeNode parent;
    private MetricSpec metricSpec;

    public MetricSpecWrapper(TreeNode parent, MetricSpec metricSpec) {
        this.parent = parent;
        this.metricSpec = metricSpec;
    }

    public TreeNode getParent() {
        return parent;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    public MetricSpec getMetricSpec() {
        return metricSpec;
    }

    public void setMetricSpec(MetricSpec metricSpec) {
        this.metricSpec = metricSpec;
    }

    @Override
    public String toString() {
        return metricSpec.getSpecName();
    }
}
