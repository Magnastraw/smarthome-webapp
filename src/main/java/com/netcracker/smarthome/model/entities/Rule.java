package com.netcracker.smarthome.model.entities;

import javax.persistence.*;

@Entity
@Table(name = "rules", schema = "public", catalog = "smarthome_db")
public class Rule {
    private long ruleId;
    private String name;
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
    @SequenceGenerator(name = "rule_seq", sequenceName = "rules_rule_id_seq")
    public long getRuleId() {
        return ruleId;
    }

    public void setRuleId(long ruleId) {
        this.ruleId = ruleId;
    }

    @Basic
    @Column(name = "name", nullable = false, length = -1)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rule rule = (Rule) o;

        if (ruleId != rule.ruleId) return false;
        if (name != null ? !name.equals(rule.name) : rule.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (ruleId ^ (ruleId >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "policy_id", referencedColumnName = "policy_id", nullable = false)
    public Policy getPolicy() {
        return policy;
    }

    public void setPolicy(Policy policy) {
        this.policy = policy;
    }
}
