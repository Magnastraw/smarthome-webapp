package com.netcracker.smarthome.model.keys;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class GroupMemberPK implements Serializable {
    private long groupId;
    private long userId;

    @Column(name = "group_id", nullable = false)
    @Id
    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    @Column(name = "user_id", nullable = false)
    @Id
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof GroupMemberPK)) return false;

        GroupMemberPK that = (GroupMemberPK) o;

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
}
