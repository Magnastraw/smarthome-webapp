package com.netcracker.smarthome.model.entities;

import com.netcracker.smarthome.model.keys.SocialProfilePK;

import javax.persistence.*;

@Entity
@Table(name = "social_profiles", schema = "public", catalog = "smarthome_db")
@IdClass(SocialProfilePK.class)
public class SocialProfile {
    private String userSocialId;
    private User user;
    private SocialService service;

    @Basic
    @Column(name = "user_social_id", nullable = false, length = -1)
    public String getUserSocialId() {
        return userSocialId;
    }

    public void setUserSocialId(String userSocialId) {
        this.userSocialId = userSocialId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SocialProfile that = (SocialProfile) o;

        if (userSocialId != null ? !userSocialId.equals(that.userSocialId) : that.userSocialId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return userSocialId != null ? userSocialId.hashCode() : 0;
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

    @Id
    @ManyToOne
    @JoinColumn(name = "service_id", referencedColumnName = "service_id", nullable = false)
    public SocialService getService() {
        return service;
    }

    public void setService(SocialService service) {
        this.service = service;
    }
}
