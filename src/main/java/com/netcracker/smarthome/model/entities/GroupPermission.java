package com.netcracker.smarthome.model.entities;

import com.netcracker.smarthome.model.keys.GroupPermissionPK;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "groups_permissions", schema = "public", catalog = "smarthome_db")
@IdClass(GroupPermissionPK.class)
public class GroupPermission implements Serializable {
    private String action;
    private long permissionId;
    private Permission permission;
    private Group group;

    public GroupPermission() {
    }

    public GroupPermission(String action, long permissionId) {
        this.action = action;
        this.permissionId = permissionId;
    }

    @Id
    @Column(name = "permission_id", nullable = false, length = -1)
    public long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(long permissionId) {
        this.permissionId = permissionId;
    }

    @Id
    @Column(name = "action", nullable = false, length = -1)
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @ManyToOne
    @JoinColumn(name = "permission_id", referencedColumnName = "permission_id", nullable = false, insertable = false, updatable = false)
    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    @ManyToOne
    @JoinColumn(name = "group_id", referencedColumnName = "group_id", nullable = false)
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof GroupPermission)) return false;

        GroupPermission that = (GroupPermission) o;

        return new EqualsBuilder()
                .append(getPermissionId(), that.getPermissionId())
                .append(getAction(), that.getAction())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getAction())
                .append(getPermissionId())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("action", getAction())
                .append("permission", getPermission())
                .append("group", getGroup())
                .toString();
    }
}
