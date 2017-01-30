package com.netcracker.smarthome.model.keys;


import com.netcracker.smarthome.model.entities.Group;
import com.netcracker.smarthome.model.entities.User;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class GroupMemberPK implements Serializable {
    private Group group;
    private User user;

    @Column(name = "group_id", nullable = false)
    @Id
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Column(name = "user_id", nullable = false)
    @Id
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GroupMemberPK that = (GroupMemberPK) o;

        if (group != null ? !group.equals(that.group) : that.group != null) return false;
        return user != null ? user.equals(that.user) : that.user == null;
    }

    @Override
    public int hashCode() {
        int result = group != null ? group.hashCode() : 0;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }
}
