package com.netcracker.smarthome.model.entities;

import com.netcracker.smarthome.model.keys.UserPermissionPK;

import javax.persistence.*;

@Entity
@Table(name = "users_permissions", schema = "public", catalog = "smarthome_db")
@IdClass(UserPermissionPK.class)
public class UserPermission {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserPermission that = (UserPermission) o;

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
}
