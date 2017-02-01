package com.netcracker.smarthome.model.entities;

import com.netcracker.smarthome.model.keys.GroupMemberPK;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "groups_members", schema = "public", catalog = "smarthome_db")
@IdClass(GroupMemberPK.class)
public class GroupMember implements Serializable {
    private boolean isAdmin;
    private long groupId;
    private long userId;
    private Group group;
    private User user;

    public GroupMember() {
    }

    public GroupMember(boolean isAdmin, long groupId, long userId) {
        this.isAdmin = isAdmin;
        this.groupId = groupId;
        this.userId = userId;
    }

    @Id
    @Column(name = "group_id", nullable = false)
    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    @Id
    @Column(name = "user_id", nullable = false)
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "is_admin", nullable = false)
    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        isAdmin = isAdmin;
    }

    @ManyToOne
    @JoinColumn(name = "group_id", referencedColumnName = "group_id", nullable = false, insertable = false, updatable = false)
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false, insertable = false, updatable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof GroupMember)) return false;

        GroupMember that = (GroupMember) o;

        return new EqualsBuilder()
                .append(getGroupId(), that.getGroupId())
                .append(getUserId(), that.getUserId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getGroupId())
                .append(getUserId())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("isAdmin", isAdmin())
                .append("group", getGroup())
                .append("user", getUser())
                .toString();
    }

}
