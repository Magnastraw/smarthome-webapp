package com.netcracker.smarthome.model.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "social_servicies", schema = "public", catalog = "smarthome_db")
public class SocialService implements Serializable {
    private long serviceId;
    private String serviceName;
    private String clientId;
    private String secretKey;
    private Collection<SocialProfile> socialProfiles;

    public SocialService() {
    }

    public SocialService(String serviceName, String clientId, String secretKey) {
        this.serviceName = serviceName;
        this.clientId = clientId;
        this.secretKey = secretKey;
    }

    @Id
    @Column(name = "service_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ss_seq")
    @SequenceGenerator(name = "ss_seq", sequenceName = "social_servicies_service_id_seq", allocationSize = 1)
    public long getServiceId() {
        return serviceId;
    }

    public void setServiceId(long serviceId) {
        this.serviceId = serviceId;
    }

    @Basic
    @Column(name = "service_name", nullable = false, length = -1)
    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    @Basic
    @Column(name = "client_id", nullable = false, length = -1)
    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Basic
    @Column(name = "secret_key", nullable = false, length = -1)
    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SocialService that = (SocialService) o;

        if (serviceId != that.serviceId) return false;
        if (serviceName != null ? !serviceName.equals(that.serviceName) : that.serviceName != null) return false;
        if (clientId != null ? !clientId.equals(that.clientId) : that.clientId != null) return false;
        if (secretKey != null ? !secretKey.equals(that.secretKey) : that.secretKey != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (serviceId ^ (serviceId >>> 32));
        result = 31 * result + (serviceName != null ? serviceName.hashCode() : 0);
        result = 31 * result + (clientId != null ? clientId.hashCode() : 0);
        result = 31 * result + (secretKey != null ? secretKey.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "service")
    public Collection<SocialProfile> getSocialProfiles() {
        return socialProfiles;
    }

    public void setSocialProfiles(Collection<SocialProfile> socialProfiles) {
        this.socialProfiles = socialProfiles;
    }

    @Override
    public String toString() {
        return serviceName;
    }
}
