package com.netcracker.smarthome.model.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "actions", schema = "public", catalog = "smarthome_db")
public class Action implements Serializable {
    private long actionId;
    private boolean type;
    private long order;
    private Rule rule;
    private Set<ActionParam> actionParams;
    private Set<ActionClass> actionClasses;

    public Action() {
    }

    public Action(boolean type, long order, Rule rule) {
        this.type = type;
        this.order = order;
        this.rule = rule;
    }

    @Id
    @Column(name = "action_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "action_seq")
    @SequenceGenerator(name = "action_seq", sequenceName = "policies_policy_seq", allocationSize = 1)
    public long getActionId() {
        return actionId;
    }

    public void setActionId(long actionId) {
        this.actionId = actionId;
    }

    @Basic
    @Column(name = "type", nullable = false)
    public boolean getType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    @Basic
    @Column(name = "action_order", nullable = false)
    public long getOrder() {
        return order;
    }

    public void setOrder(long order) {
        this.order = order;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "action", cascade = CascadeType.ALL)
    public Set<ActionParam> getActionParams() {
        return actionParams;
    }

    public void setActionParams(Set<ActionParam> actionParams) {
        this.actionParams = actionParams;
    }

    @ManyToOne
    @JoinColumn(name = "rule_id", referencedColumnName = "rule_id", nullable = false)
    public Rule getRule() {
        return rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "action", cascade = CascadeType.ALL)
    public Set<ActionClass> getActionClasses() {
        return actionClasses;
    }

    public void setActionClasses(Set<ActionClass> actionClasses) {
        this.actionClasses = actionClasses;
    }

    @Transient
    public ActionClass getCoreClass() {
        return actionClasses == null ? null : actionClasses.stream().filter(actionClass -> actionClass.getContext().equalsIgnoreCase("core")).findAny().orElse(null);
    }

    @Transient
    public ActionClass getUiClass() {
        return actionClasses == null ? null : actionClasses.stream().filter(actionClass -> actionClass.getContext().equalsIgnoreCase("ui")).findAny().orElse(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Action)) return false;
        Action action = (Action) o;
        return new EqualsBuilder()
                .append(getActionId(), action.getActionId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getActionId())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("actionId", getActionId())
                .append("type", getType())
                .append("order", getOrder())
                .append("rule", getRule())
                .toString();
    }
}
