package com.netcracker.smarthome.model.entities;

import com.netcracker.smarthome.model.keys.GroupPermissionPK;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "groups_permissions", schema = "public", catalog = "smarthome_db")
@IdClass(GroupPermissionPK.class)
public class GroupPermission implements Serializable {
    private String action;
    private Permission permission;
    private Group group;

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

        if (action != null ? !action.equals(that.action) : that.action != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return action != null ? action.hashCode() : 0;
    }

    @Id
    @ManyToOne
    @JoinColumn(name = "permission_id", referencedColumnName = "permission_id", nullable = false)
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
