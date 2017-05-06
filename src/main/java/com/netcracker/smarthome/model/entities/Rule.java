package com.netcracker.smarthome.model.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "rules", schema = "public", catalog = "smarthome_db")
public class Rule implements Serializable {
    private long ruleId;
    private String name;
    private Set<Action> actions;
    private Set<Condition> conditions;
    private Policy policy;

    public Rule() {
    }

    public Rule(String name, Policy policy) {
        this.name = name;
        this.policy = policy;
    }

    @Id
    @Column(name = "rule_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rule_seq")
    @SequenceGenerator(name = "rule_seq", sequenceName = "policies_policy_seq", allocationSize = 1)
    public long getRuleId() {
        return ruleId;
    }

    public void setRuleId(long ruleId) {
        this.ruleId = ruleId;
    }

    @Basic
    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "rule", cascade = CascadeType.ALL)
    @OrderBy(value = "type, order asc")
    public Set<Action> getActions() {
        return actions;
    }

    public void setActions(Set<Action> actions) {
        this.actions = actions;
    }

    @OneToMany(mappedBy = "rule", cascade = CascadeType.ALL)
    public Set<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(Set<Condition> conditions) {
        this.conditions = conditions;
    }

    @ManyToOne
    @JoinColumn(name = "policy_id", referencedColumnName = "policy_id", nullable = false)
    public Policy getPolicy() {
        return policy;
    }

    public void setPolicy(Policy policy) {
        this.policy = policy;
    }

    @Transient
    public Condition getRootCondition() {
        return conditions == null ? null : conditions.stream().filter(condition -> condition.getParentNode() == null).findAny().orElse(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Rule)) return false;

        Rule rule = (Rule) o;

        return new EqualsBuilder()
                .append(getRuleId(), rule.getRuleId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getRuleId())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("ruleId", getRuleId())
                .append("name", getName())
                .append("policy", getPolicy())
                .toString();
    }
}
