package com.netcracker.smarthome.model.keys;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class GroupPermissionPK implements Serializable {
    private String action;
    private long permissionId;

    public GroupPermissionPK() {
    }

    public GroupPermissionPK(String action, long permissionId) {
        this.action = action;
        this.permissionId = permissionId;
    }

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

        if (!(o instanceof GroupPermissionPK)) return false;

        GroupPermissionPK that = (GroupPermissionPK) o;

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
}
