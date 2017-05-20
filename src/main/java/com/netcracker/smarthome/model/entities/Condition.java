package com.netcracker.smarthome.model.entities;

import com.netcracker.smarthome.model.enums.BooleanOperator;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "conditions", schema = "public", catalog = "smarthome_db")
public class Condition implements Serializable {
    private long nodeId;
    private BooleanOperator operator;
    private Set<ConditionParam> conditionParams;
    private Rule rule;
    private Condition parentNode;
    private Set<Condition> childNodes;
    private Set<ConditionClass> conditionClasses;

    public Condition() {
    }

    public Condition(BooleanOperator operator, Rule rule, Condition nextCondition) {
        this.operator = operator;
        this.rule = rule;
        this.parentNode = nextCondition;
    }

    @Id
    @Column(name = "node_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cond_seq")
    @SequenceGenerator(name = "cond_seq", sequenceName = "policies_policy_seq", allocationSize = 1)
    public long getNodeId() {
        return nodeId;
    }

    public void setNodeId(long nodeId) {
        this.nodeId = nodeId;
    }

    @Basic
    @Column(name = "operator", nullable = true)
    @Enumerated(EnumType.ORDINAL)
    public BooleanOperator getOperator() {
        return operator;
    }

    public void setOperator(BooleanOperator operator) {
        this.operator = operator;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "condition", cascade = CascadeType.ALL)
    public Set<ConditionParam> getConditionParams() {
        return conditionParams;
    }

    public void setConditionParams(Set<ConditionParam> conditionParams) {
        this.conditionParams = conditionParams;
    }

    @ManyToOne
    @JoinColumn(name = "rule_id", referencedColumnName = "rule_id", nullable = false)
    public Rule getRule() {
        return rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    @ManyToOne
    @JoinColumn(name = "parent_node_id", referencedColumnName = "node_id")
    public Condition getParentNode() {
        return parentNode;
    }

    public void setParentNode(Condition nextCondition) {
        this.parentNode = nextCondition;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "parentNode")
    public Set<Condition> getChildNodes() {
        return childNodes;
    }

    public void setChildNodes(Set<Condition> childNodes) {
        this.childNodes = childNodes;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "condition")
    public Set<ConditionClass> getConditionClasses() {
        return conditionClasses;
    }

    public void setConditionClasses(Set<ConditionClass> conditionClasses) {
        this.conditionClasses = conditionClasses;
    }

    @Transient
    public ConditionClass getCoreClass() {
        return conditionClasses == null ? null : conditionClasses.stream().filter(actionClass -> actionClass.getContext().equalsIgnoreCase("core")).findAny().orElse(null);
    }

    @Transient
    public ConditionClass getUiClass() {
        return conditionClasses == null ? null : conditionClasses.stream().filter(actionClass -> actionClass.getContext().equalsIgnoreCase("ui")).findAny().orElse(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Condition)) return false;
        Condition condition = (Condition) o;
        return new EqualsBuilder()
                .append(getNodeId(), condition.getNodeId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getNodeId())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("nodeId", getNodeId())
                .append("operator", getOperator())
                .append("rule", getRule())
                .append("parentNode", getParentNode())
                .toString();
    }
}
