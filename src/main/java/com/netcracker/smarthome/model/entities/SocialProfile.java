package com.netcracker.smarthome.model.entities;

import com.netcracker.smarthome.model.keys.SocialProfilePK;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "social_profiles", schema = "public", catalog = "smarthome_db")
@IdClass(SocialProfilePK.class)
public class SocialProfile implements Serializable {
    private long userId;
    private long serviceId;
    private String userSocialId;
    private User user;
    private SocialService socialService;

    public SocialProfile() {
    }

    public SocialProfile(long userId, long serviceId, String userSocialId) {
        this.userId = userId;
        this.serviceId = serviceId;
        this.userSocialId = userSocialId;
    }

    @Id
    @Column(name = "user_id", nullable = false)
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Id
    @Column(name = "service_id", nullable = false)
    public long getServiceId() {
        return serviceId;
    }

    public void setServiceId(long serviceId) {
        this.serviceId = serviceId;
    }

    @Basic
    @Column(name = "user_social_id", nullable = false)
    public String getUserSocialId() {
        return userSocialId;
    }

    public void setUserSocialId(String userSocialId) {
        this.userSocialId = userSocialId;
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
    public SocialService getSocialService() {
        return socialService;
    }

    public void setSocialService(SocialService socialService) {
        this.socialService = socialService;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof SocialProfile)) return false;

        SocialProfile that = (SocialProfile) o;

        return new EqualsBuilder()
                .append(getUserId(), that.getUserId())
                .append(getServiceId(), that.getServiceId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getUserId())
                .append(getServiceId())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("userSocialId", getUserSocialId())
                .append("user", getUser())
                .append("service", getSocialService())
                .toString();
    }
}
