package com.netcracker.smarthome.web.policy;

import com.netcracker.smarthome.business.policy.services.RuleService;
import com.netcracker.smarthome.model.entities.Policy;
import com.netcracker.smarthome.model.entities.Rule;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.util.LinkedList;
import java.util.List;

@ManagedBean
@SessionScoped
public class RulesBean {
    private Policy currentPolicy;
    private RuleItem selectedRule;
    private List<RuleItem> tableRules;
    @ManagedProperty(value = "#{ruleService}")
    private RuleService ruleService;
    @ManagedProperty(value = "#{policyBean}")
    private PoliciesBean policyBean;

    @PostConstruct
    public void initialize(){
        PoliciesTableItem policiesTableItem = policyBean.getSelectedItem();
        currentPolicy = new Policy(policiesTableItem.getName(), policiesTableItem.getStatus(), policiesTableItem.getParentCatalog(), policiesTableItem.getDescription());
        tableRules = new LinkedList<RuleItem>();
        List<Rule> rules = ruleService.getRulesByPolicy(currentPolicy);
        for (Rule rule : rules) {
            tableRules.add(new RuleItem(rule));
        }
    }

    public RulesBean() {
        selectedRule = getDefaultRule();
    }

    public RuleItem getDefaultRule() {
        return new RuleItem(new Rule("New rule", currentPolicy));
    }
}
