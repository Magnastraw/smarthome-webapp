package com.netcracker.smarthome.model.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "permissions", schema = "public", catalog = "smarthome_db")
public class Permission implements Serializable {
    private long permissionId;
    private Collection<GroupPermission> groups;
    private AbstractObject abstractObject;
    private Collection<UserPermission> users;

    public Permission() {
    }

    public Permission(AbstractObject abstractObject) {
        this.abstractObject = abstractObject;
    }

    @Id
    @Column(name = "permission_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "perm_seq")
    @SequenceGenerator(name = "perm_seq", sequenceName = "permissions_permission_id_seq", allocationSize = 1)
    public long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(long permissionId) {
        this.permissionId = permissionId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Permission)) return false;

        Permission that = (Permission) o;

        return new EqualsBuilder()
                .append(getPermissionId(), that.getPermissionId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getPermissionId())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("permissionId", getPermissionId())
                .append("abstractObject", getAbstractObject())
                .toString();
    }
}
