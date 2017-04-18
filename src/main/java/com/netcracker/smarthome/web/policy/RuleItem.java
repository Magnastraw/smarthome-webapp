package com.netcracker.smarthome.web.policy;

import com.netcracker.smarthome.model.entities.Rule;

public class RuleItem {
    private Rule rule;

    public RuleItem(Rule rule) {
        this.rule = rule;
    }

    public Rule getRuleItem() {
        return rule;
    }

    public String getName() {
        return rule.getName();
    }
}
