package com.netcracker.smarthome.model.entities;

import com.netcracker.smarthome.model.keys.UserPermissionPK;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users_permissions", schema = "public", catalog = "smarthome_db")
@IdClass(UserPermissionPK.class)
public class UserPermission implements Serializable {
    private String action;
    private long permissionId;
    private Permission permission;
    private User admin;
    private User user;

    public UserPermission() {
    }

    public UserPermission(String action, long permissionId, User admin, User user) {
        this.action = action;
        this.permissionId = permissionId;
        this.admin = admin;
        this.user = user;
    }

    @Id
    @Column(name = "permission_id", nullable = false)
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
    @JoinColumn(name = "admin_id", referencedColumnName = "user_id", nullable = false)
    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof UserPermission)) return false;

        UserPermission that = (UserPermission) o;

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
                .append("admin", getAdmin())
                .append("user", getUser())
                .toString();
    }
}
