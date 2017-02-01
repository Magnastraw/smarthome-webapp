package com.netcracker.smarthome.model.entities;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractObject that = (AbstractObject) o;

        if (objectId != that.objectId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (objectId ^ (objectId >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "abstractObject")
    public Collection<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Collection<Permission> permissions) {
        this.permissions = permissions;
    }
}
