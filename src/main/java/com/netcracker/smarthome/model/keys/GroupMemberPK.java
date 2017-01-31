package com.netcracker.smarthome.model.keys;


import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class GroupMemberPK implements Serializable {
    private long groupId;
    private long userId;

    public GroupMemberPK() {
    }

    public GroupMemberPK(long groupId, long userId) {
        this.groupId = groupId;
        this.userId = userId;
    }

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
        if (o == null || getClass() != o.getClass()) return false;

        GroupMemberPK that = (GroupMemberPK) o;

        if (groupId != that.groupId) return false;
        return userId == that.userId;
    }

    @Override
    public int hashCode() {
        int result = (int) (groupId ^ (groupId >>> 32));
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        return result;
    }
}
