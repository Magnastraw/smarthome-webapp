package com.netcracker.smarthome.model.entities;

import com.netcracker.smarthome.model.keys.GroupMemberPK;

import javax.persistence.*;

@Entity
@Table(name = "groups_members", schema = "public", catalog = "smarthome_db")
@IdClass(GroupMemberPK.class)
public class GroupMember {
    private boolean isAdmin;
    private Group group;
    private User user;

    @Basic
    @Column(name = "is_admin", nullable = false)
    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GroupMember that = (GroupMember) o;

        if (isAdmin != that.isAdmin) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (isAdmin ? 1 : 0);
    }

    @Id
    @ManyToOne
    @JoinColumn(name = "group_id", referencedColumnName = "group_id", nullable = false)
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
