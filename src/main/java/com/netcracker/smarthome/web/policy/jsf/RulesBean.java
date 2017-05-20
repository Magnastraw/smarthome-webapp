package com.netcracker.smarthome.web.policy.jsf;

import com.netcracker.smarthome.business.services.PolicyService;
import com.netcracker.smarthome.model.entities.Policy;
import com.netcracker.smarthome.model.entities.Rule;
import com.netcracker.smarthome.web.policy.ContentBuilder;
import com.netcracker.smarthome.web.policy.UIInitializer;
import com.netcracker.smarthome.web.policy.components.UIComponent;
import com.netcracker.smarthome.web.policy.components.UIConditionTreeNode;
import com.netcracker.smarthome.web.policy.components.UIOperator;
import com.netcracker.smarthome.web.policy.components.UIRule;
import org.primefaces.model.OrganigramNode;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.util.List;
import java.util.stream.Collectors;

@ManagedBean
@SessionScoped
public class RulesBean {
    @ManagedProperty("#{UIInitializer}")
    private UIInitializer initializer;
    @ManagedProperty("#{policyService}")
    private PolicyService policyService;
    private Policy policy;
    private List<UIRule> rules;
    private UIRule currentRule;
    private OrganigramNode currentRuleRootNode;
    private ContentBuilder contentBuilder;
    private OrganigramNode selectedNode;

    public RulesBean() {
        contentBuilder = new ContentBuilder();
    }

    private void initializeRules() {
        List<Rule> dbRules = policyService.getRules(policy.getPolicyId());
        rules = dbRules
                .stream()
                .map(rule -> initializer.buildRule(rule.getRuleId()))
                .collect(Collectors.toList());
        currentRule = rules.isEmpty() ? null : rules.get(0);
        if (currentRule != null)
            initializeConditionTree();
    }

    private void initializeConditionTree() {
        if (currentRule.getConditionTreeRoot() != null)
            currentRuleRootNode = createConditionSubtree(currentRule.getConditionTreeRoot(), null);
        else
            currentRuleRootNode = null;
    }

    private OrganigramNode createConditionSubtree(UIConditionTreeNode subtreeRoot, OrganigramNode parent) {
        OrganigramNode root = new ReadOnlyOrganigramNode(getType(subtreeRoot), subtreeRoot, parent);
        if (subtreeRoot instanceof UIOperator) {
            for (UIConditionTreeNode child : ((UIOperator) subtreeRoot).getChildren())
                createConditionSubtree(child, root);
        }
        return root;
    }

    private String getType(UIConditionTreeNode node) {
        return node instanceof UIOperator ? "operator" : "condition";
    }

    public Object getContent(UIComponent component) {
        return contentBuilder.getContent(true, component);
    }

    public void setInitializer(UIInitializer initializer) {
        this.initializer = initializer;
    }

    public Policy getPolicy() {
        return policy;
    }

    public void setPolicy(Policy policy) {
        this.policy = policy;
        initializeRules();
    }

    public UIRule getCurrentRule() {
        return currentRule;
    }

    public void setCurrentRule(UIRule currentRule) {
        this.currentRule = currentRule;
        initializeConditionTree();
    }

    public void setPolicyService(PolicyService policyService) {
        this.policyService = policyService;
    }

    public List<UIRule> getRules() {
        return rules;
    }

    public void setRules(List<UIRule> rules) {
        this.rules = rules;
    }

    public OrganigramNode getCurrentRuleRootNode() {
        return currentRuleRootNode;
    }

    public void setCurrentRuleRootNode(OrganigramNode currentRuleRootNode) {
        this.currentRuleRootNode = currentRuleRootNode;
    }

    public OrganigramNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(OrganigramNode selectedNode) {
        this.selectedNode = selectedNode;
    }
}
