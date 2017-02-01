package com.netcracker.smarthome.model.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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

    public Group() {
    }

    public Group(String groupName) {
        this.groupName = groupName;
    }

    @Id
    @Column(name = "group_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "group_seq")
    @SequenceGenerator(name = "group_seq", sequenceName = "groups_group_id_seq", allocationSize = 1)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Group)) return false;

        Group group = (Group) o;

        return new EqualsBuilder()
                .append(getGroupId(), group.getGroupId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getGroupId())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("groupId", getGroupId())
                .append("groupName", getGroupName())
                .toString();
    }
}
