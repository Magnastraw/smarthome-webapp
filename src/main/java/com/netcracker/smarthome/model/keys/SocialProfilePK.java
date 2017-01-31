package com.netcracker.smarthome.model.keys;

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
        if (o == null || getClass() != o.getClass()) return false;

        SocialProfilePK that = (SocialProfilePK) o;

        if (userId != that.userId) return false;
        return serviceId == that.serviceId;
    }

    @Override
    public int hashCode() {
        int result = (int) (userId ^ (userId >>> 32));
        result = 31 * result + (int) (serviceId ^ (serviceId >>> 32));
        return result;
    }
}
