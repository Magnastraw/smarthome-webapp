package com.netcracker.smarthome.model.entities;

import com.netcracker.smarthome.model.keys.GroupPermissionPK;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GroupPermission that = (GroupPermission) o;

        if (permissionId != that.permissionId) return false;
        return action != null ? action.equals(that.action) : that.action == null;
    }

    @Override
    public int hashCode() {
        int result = action != null ? action.hashCode() : 0;
        result = 31 * result + (int) (permissionId ^ (permissionId >>> 32));
        return result;
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
}
