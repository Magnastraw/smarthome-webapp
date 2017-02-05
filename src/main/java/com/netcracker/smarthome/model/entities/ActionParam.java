package com.netcracker.smarthome.model.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "action_params", schema = "public", catalog = "smarthome_db")
public class ActionParam implements Serializable {
    private long paramId;
    private String name;
    private String value;
    private Action action;

    public ActionParam() {
    }

    public ActionParam(String name, String value, Action action) {
        this.name = name;
        this.value = value;
        this.action = action;
    }

    @Id
    @Column(name = "param_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "a_param_seq")
    @SequenceGenerator(name = "a_param_seq", sequenceName = "action_params_param_id_seq", allocationSize = 1)
    public long getParamId() {
        return paramId;
    }

    public void setParamId(long paramId) {
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
    @JoinColumn(name = "action_id", referencedColumnName = "action_id", nullable = false)
    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof ActionParam)) return false;

        ActionParam that = (ActionParam) o;

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
                .append("action", getAction())
                .toString();
    }
}
