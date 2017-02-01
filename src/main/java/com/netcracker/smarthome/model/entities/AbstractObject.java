package com.netcracker.smarthome.model.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "abstract_objects", schema = "public", catalog = "smarthome_db")
public class AbstractObject implements Serializable {
    private long objectId;
    private String name;
    private String description;
    private Collection<Permission> permissions;

    public AbstractObject() {
    }

    public AbstractObject(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Id
    @Column(name = "object_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "abstract_object_seq")
    @SequenceGenerator(name = "abstract_object_seq", sequenceName = "abstract_objects_object_id_seq", allocationSize = 1)
    public long getObjectId() {
        return objectId;
    }

    public void setObjectId(long objectId) {
        this.objectId = objectId;
    }

    @Basic
    @Column(name = "name", nullable = false, length = -1)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "description", nullable = true, length = -1)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany(mappedBy = "abstractObject")
    public Collection<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Collection<Permission> permissions) {
        this.permissions = permissions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof AbstractObject)) return false;

        AbstractObject that = (AbstractObject) o;

        return new EqualsBuilder()
                .append(getObjectId(), that.getObjectId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getObjectId())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("objectId", getObjectId())
                .append("name", getName())
                .toString();
    }
}
