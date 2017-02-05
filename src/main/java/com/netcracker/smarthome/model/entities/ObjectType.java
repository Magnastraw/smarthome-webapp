package com.netcracker.smarthome.model.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "object_types", schema = "public", catalog = "smarthome_db")
public class ObjectType implements Serializable {
    private long objectTypeId;
    private String name;
    private String description;
    private List<SmartObject> objects;

    public ObjectType() {
    }

    public ObjectType(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Id
    @Column(name = "object_type_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "o_type_seq")
    @SequenceGenerator(name = "o_type_seq", sequenceName = "object_types_type_id_seq", allocationSize = 1)
    public long getObjectTypeId() {
        return objectTypeId;
    }

    public void setObjectTypeId(long objectTypeId) {
        this.objectTypeId = objectTypeId;
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

    @OneToMany(mappedBy = "objectType")
    public List<SmartObject> getObjects() {
        return objects;
    }

    public void setObjects(List<SmartObject> objects) {
        this.objects = objects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof ObjectType)) return false;

        ObjectType that = (ObjectType) o;

        return new EqualsBuilder()
                .append(getObjectTypeId(), that.getObjectTypeId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getObjectTypeId())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("objectTypeId", getObjectTypeId())
                .append("name", getName())
                .append("description", getDescription())
                .toString();
    }
}
