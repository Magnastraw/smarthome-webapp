package com.netcracker.smarthome.model.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "permissions", schema = "public", catalog = "smarthome_db")
public class Permission implements Serializable {
    private long permissionId;
    private int actionsMask;
    private long permObject;
    private User admin;
    private User user;
    private Group group;

    public Permission() {
    }

    public Permission(int actionsMask, long permObject, User admin, User user, Group group) {
        this.actionsMask = actionsMask;
        this.permObject = permObject;
        this.admin = admin;
        this.user = user;
        this.group = group;
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

    @Basic
    @Column(name = "actions_mask", nullable = false)
    public int getActionsMask() {
        return actionsMask;
    }

    public void setActionsMask(int actionsMask) {
        this.actionsMask = actionsMask;
    }

    @Basic
    @Column(name = "perm_object", nullable = false)
    public long getPermObject() {
        return permObject;
    }

    public void setPermObject(long permObject) {
        this.permObject = permObject;
    }

    @ManyToOne
    @JoinColumn(name = "admin_id", referencedColumnName = "user_id", nullable = false)
    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name = "group_id", referencedColumnName = "group_id")
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
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
                .append("actionsMask", getActionsMask())
                .append("permObject", getPermObject())
                .append("admin", getAdmin())
                .append("user", getUser())
                .append("group",getGroup())
                .toString();
    }
}
