package com.netcracker.smarthome.model.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "condition_types", schema = "public", catalog = "smarthome_db")
public class ConditionType implements Serializable {
    private String typeId;
    private String name;
    private String description;
    private String conditionClass;
    private List<Condition> conditions;

    public ConditionType() {
    }

    public ConditionType(String name, String description, String conditionClass) {
        this.name = name;
        this.description = description;
        this.conditionClass = conditionClass;
    }

    @Id
    @Column(name = "type_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "c_type_seq")
    @SequenceGenerator(name = "c_type_seq", sequenceName = "condition_types_type_id_seq", allocationSize = 1)
    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
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
    @Column(name = "description", nullable = false)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "condition_class", nullable = false)
    public String getConditionClass() {
        return conditionClass;
    }

    public void setConditionClass(String conditionClass) {
        this.conditionClass = conditionClass;
    }

    @OneToMany(mappedBy = "conditionType")
    public List<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof ConditionType)) return false;

        ConditionType that = (ConditionType) o;

        return new EqualsBuilder()
                .append(getTypeId(), that.getTypeId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getTypeId())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("typeId", getTypeId())
                .append("name", getName())
                .append("description", getDescription())
                .append("conditionClass", getConditionClass())
                .toString();
    }
}
