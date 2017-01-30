package com.netcracker.smarthome.model.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "groups", schema = "public", catalog = "smarthome_db")
public class Group implements Serializable {
    private long groupId;
    private String groupName;
    private Collection<GroupMember> groupMembers;
    private Collection<GroupPermission> groupPermissions;

    @Id
    @Column(name = "group_id", nullable = false)
    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    @Basic
    @Column(name = "group_name", nullable = false, length = -1)
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Group group = (Group) o;

        if (groupId != group.groupId) return false;
        if (groupName != null ? !groupName.equals(group.groupName) : group.groupName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (groupId ^ (groupId >>> 32));
        result = 31 * result + (groupName != null ? groupName.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "group")
    public Collection<GroupMember> getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(Collection<GroupMember> groupMembers) {
        this.groupMembers = groupMembers;
    }

    @OneToMany(mappedBy = "group")
    public Collection<GroupPermission> getGroupPermissions() {
        return groupPermissions;
    }

    public void setGroupPermissions(Collection<GroupPermission> groupPermissions) {
        this.groupPermissions = groupPermissions;
    }
}
