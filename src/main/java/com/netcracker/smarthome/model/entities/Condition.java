package com.netcracker.smarthome.model.entities;

import com.netcracker.smarthome.model.enums.BooleanOperator;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "conditions", schema = "public", catalog = "smarthome_db")
public class Condition implements Serializable {
    private long conditionId;
    private BooleanOperator operator;
    private List<ConditionParam> conditionParams;
    private Rule rule;
    private ConditionType conditionType;
    private Condition nextCondition;
    private List<Condition> prevConditions;

    public Condition() {
    }

    public Condition(BooleanOperator operator, Rule rule, ConditionType conditionType, Condition nextCondition) {
        this.operator = operator;
        this.rule = rule;
        this.conditionType = conditionType;
        this.nextCondition = nextCondition;
    }

    @Id
    @Column(name = "condition_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cond_seq")
    @SequenceGenerator(name = "cond_seq", sequenceName = "conditions_condition_id_seq", allocationSize = 1)
    public long getConditionId() {
        return conditionId;
    }

    public void setConditionId(long conditionId) {
        this.conditionId = conditionId;
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

    @OneToMany(mappedBy = "condition", cascade = CascadeType.ALL)
    public List<ConditionParam> getConditionParams() {
        return conditionParams;
    }

    public void setConditionParams(List<ConditionParam> conditionParams) {
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
    @JoinColumn(name = "type_id", referencedColumnName = "type_id", nullable = false)
    public ConditionType getConditionType() {
        return conditionType;
    }

    public void setConditionType(ConditionType conditionType) {
        this.conditionType = conditionType;
    }

    @ManyToOne
    @JoinColumn(name = "next_condition_id", referencedColumnName = "condition_id")
    public Condition getNextCondition() {
        return nextCondition;
    }

    public void setNextCondition(Condition nextCondition) {
        this.nextCondition = nextCondition;
    }

    @OneToMany(mappedBy = "nextCondition")
    public List<Condition> getPrevConditions() {
        return prevConditions;
    }

    public void setPrevConditions(List<Condition> prevConditions) {
        this.prevConditions = prevConditions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Condition)) return false;

        Condition condition = (Condition) o;

        return new EqualsBuilder()
                .append(getConditionId(), condition.getConditionId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getConditionId())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("conditionId", getConditionId())
                .append("operator", getOperator())
                .append("rule", getRule())
                .append("conditionType", getConditionType())
                .append("nextCondition", getNextCondition())
                .toString();
    }
}
