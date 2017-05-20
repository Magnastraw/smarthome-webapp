package com.netcracker.smarthome.web.policy.components;

import java.util.ArrayList;
import java.util.List;

public class UIRule {
    private String name;
    private UIConditionTreeNode conditionTreeRoot;
    private List<UIAction> thenActions;
    private List<UIAction> elseActions;

    public UIRule(String name) {
        this.name = name;
        this.thenActions = new ArrayList<>();
        this.elseActions = new ArrayList<>();
    }

    public UIRule(String name, UIConditionTreeNode conditionTreeRoot, List<UIAction> thenActions, List<UIAction> elseActions) {
        this.name = name;
        this.conditionTreeRoot = conditionTreeRoot;
        this.thenActions = thenActions;
        this.elseActions = elseActions;
    }

    public String getName() {
        return name;
    }

    public UIConditionTreeNode getConditionTreeRoot() {
        return conditionTreeRoot;
    }

    public List<UIAction> getThenActions() {
        return thenActions;
    }

    public List<UIAction> getElseActions() {
        return elseActions;
    }
}
