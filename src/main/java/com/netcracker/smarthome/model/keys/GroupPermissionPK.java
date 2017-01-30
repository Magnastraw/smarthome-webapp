package com.netcracker.smarthome.model.keys;

import com.netcracker.smarthome.model.entities.Permission;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class GroupPermissionPK implements Serializable {
    private String action;
    private Permission permission;

    @Column(name = "action", nullable = false)
    @Id
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Column(name = "permission_id", nullable = false)
    @Id
    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GroupPermissionPK that = (GroupPermissionPK) o;

        if (action != null ? !action.equals(that.action) : that.action != null) return false;
        return permission != null ? permission.equals(that.permission) : that.permission == null;
    }

    @Override
    public int hashCode() {
        int result = action != null ? action.hashCode() : 0;
        result = 31 * result + (permission != null ? permission.hashCode() : 0);
        return result;
    }
}
