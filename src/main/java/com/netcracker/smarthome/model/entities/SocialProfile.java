package com.netcracker.smarthome.model.entities;

import com.netcracker.smarthome.model.keys.SocialProfilePK;

import javax.persistence.*;

@Entity
@Table(name = "social_profiles", schema = "public", catalog = "smarthome_db")
@IdClass(SocialProfilePK.class)
public class SocialProfile {
    private String userSocialId;
    private long userId;
    private long serviceId;
    private User user;
    private SocialService service;

    @Column(name = "user_id", nullable = false)
    @Id
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Column(name = "service_id", nullable = false)
    @Id
    public long getServiceId() {
        return serviceId;
    }

    public void setServiceId(long serviceId) {
        this.serviceId = serviceId;
    }

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

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false, insertable = false, updatable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name = "service_id", referencedColumnName = "service_id", nullable = false, insertable = false, updatable = false)
    public SocialService getService() {
        return service;
    }

    public void setService(SocialService service) {
        this.service = service;
    }
}
