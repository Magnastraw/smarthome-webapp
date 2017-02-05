package com.netcracker.smarthome.model.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "condition_params", schema = "public", catalog = "smarthome_db")
public class ConditionParam implements Serializable {
    private String paramId;
    private String name;
    private String value;
    private Condition condition;

    public ConditionParam() {
    }

    public ConditionParam(String name, String value, Condition condition) {
        this.name = name;
        this.value = value;
        this.condition = condition;
    }

    @Id
    @Column(name = "param_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "c_param_seq")
    @SequenceGenerator(name = "c_param_seq", sequenceName = "condition_params_param_id_seq", allocationSize = 1)
    public String getParamId() {
        return paramId;
    }

    public void setParamId(String paramId) {
        this.paramId = paramId;
    }

    @Basic
    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "value", nullable = false)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @ManyToOne
    @JoinColumn(name = "condition_id", referencedColumnName = "condition_id", nullable = false)
    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof ConditionParam)) return false;

        ConditionParam that = (ConditionParam) o;

        return new EqualsBuilder()
                .append(getParamId(), that.getParamId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getParamId())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("paramId", getParamId())
                .append("name", getName())
                .append("value", getValue())
                .append("condition", getCondition())
                .toString();
    }
}
