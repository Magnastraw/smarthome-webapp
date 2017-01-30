package com.netcracker.smarthome.model.keys;

import com.netcracker.smarthome.model.entities.SocialService;
import com.netcracker.smarthome.model.entities.User;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class SocialProfilePK implements Serializable {
    private User user;
    private SocialService service;

    @Column(name = "user_id", nullable = false)
    @Id
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "service_id", nullable = false)
    @Id
    public SocialService getService() {
        return service;
    }

    public void setService(SocialService service) {
        this.service = service;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SocialProfilePK that = (SocialProfilePK) o;

        if (user != null ? !user.equals(that.user) : that.user != null) return false;
        return service != null ? service.equals(that.service) : that.service == null;
    }

    @Override
    public int hashCode() {
        int result = user != null ? user.hashCode() : 0;
        result = 31 * result + (service != null ? service.hashCode() : 0);
        return result;
    }
}
