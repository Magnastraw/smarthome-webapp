package com.netcracker.smarthome.model.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;

@Entity
@Table(name = "condition_classes", schema = "public", catalog = "smarthome_db")
public class ConditionClass {
    private long classId;
    private String name;
    private String context;
    private Condition condition;

    public ConditionClass() {
    }

    public ConditionClass(String name, String context, Condition condition) {
        this.name = name;
        this.context = context;
        this.condition = condition;
    }

    @Id
    @Column(name = "class_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "c_class_seq")
    @SequenceGenerator(name = "c_class_seq", sequenceName = "condition_classes_class_id_seq", allocationSize = 1)
    public long getClassId() {
        return classId;
    }

    public void setClassId(long paramId) {
        this.classId = paramId;
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
    @Column(name = "context", nullable = false)
    public String getContext() {
        return context;
    }

    public void setContext(String value) {
        this.context = value;
    }

    @ManyToOne
    @JoinColumn(name = "condition_id", referencedColumnName = "node_id", nullable = false)
    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof ConditionClass)) return false;

        ConditionClass that = (ConditionClass) o;

        return new EqualsBuilder()
                .append(getClassId(), that.getClassId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getClassId())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("classId", getClassId())
                .append("name", getName())
                .append("context", getContext())
                .append("condition", getCondition())
                .toString();
    }
}
