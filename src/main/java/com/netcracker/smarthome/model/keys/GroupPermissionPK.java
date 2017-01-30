package com.netcracker.smarthome.model.keys;

import com.netcracker.smarthome.model.entities.Permission;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class GroupPermissionPK implements Serializable {
    private String action;
    private long permissionId;

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
    public long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(long permission) {
        this.permissionId = permissionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GroupPermissionPK that = (GroupPermissionPK) o;

        if (permissionId != that.permissionId) return false;
        return action != null ? action.equals(that.action) : that.action == null;
    }

    @Override
    public int hashCode() {
        int result = action != null ? action.hashCode() : 0;
        result = 31 * result + (int) (permissionId ^ (permissionId >>> 32));
        return result;
    }
}
