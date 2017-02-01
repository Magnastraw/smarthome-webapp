package com.netcracker.smarthome.model.keys;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class SocialProfilePK implements Serializable {
    private long userId;
    private long serviceId;

    public SocialProfilePK() {}

    public SocialProfilePK(long userId, long serviceId) {
        this.userId = userId;
        this.serviceId = serviceId;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof SocialProfilePK)) return false;

        SocialProfilePK that = (SocialProfilePK) o;

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
}
