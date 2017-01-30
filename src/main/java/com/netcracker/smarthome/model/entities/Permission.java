package com.netcracker.smarthome.model.entities;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "permissions", schema = "public", catalog = "smarthome_db")
public class Permission {
    private long permissionId;
    private Collection<GroupPermission> groups;
    private AbstractObject abstractObject;
    private Collection<UserPermission> users;

    @Id
    @Column(name = "permission_id", nullable = false)
    public long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(long permissionId) {
        this.permissionId = permissionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Permission that = (Permission) o;

        if (permissionId != that.permissionId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (permissionId ^ (permissionId >>> 32));
    }

    @OneToMany(mappedBy = "permission")
    public Collection<GroupPermission> getGroups() {
        return groups;
    }

    public void setGroups(Collection<GroupPermission> groups) {
        this.groups = groups;
    }

    @ManyToOne
    @JoinColumn(name = "object_id", referencedColumnName = "object_id", nullable = false)
    public AbstractObject getAbstractObject() {
        return abstractObject;
    }

    public void setAbstractObject(AbstractObject abstractObject) {
        this.abstractObject = abstractObject;
    }

    @OneToMany(mappedBy = "permission")
    public Collection<UserPermission> getUsers() {
        return users;
    }

    public void setUsers(Collection<UserPermission> users) {
        this.users = users;
    }
}
