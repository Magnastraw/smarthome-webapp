package com.netcracker.smarthome.model.entities;

import com.netcracker.smarthome.model.enums.AuthService;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "social_services", schema = "public", catalog = "smarthome_db")
public class SocialService implements Serializable {
    private long serviceId;
    private AuthService serviceType;
    private String serviceName;
    private String clientId;
    private String secretKey;
    private List<SocialProfile> socialProfiles;

    public SocialService() {
    }

    public SocialService(AuthService serviceType, String serviceName, String clientId, String secretKey) {
        this.serviceType = serviceType;
        this.serviceName = serviceName;
        this.clientId = clientId;
        this.secretKey = secretKey;
    }

    @Id
    @Column(name = "service_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ss_seq")
    @SequenceGenerator(name = "ss_seq", sequenceName = "social_services_service_id_seq", allocationSize = 1)
    public long getServiceId() {
        return serviceId;
    }

    public void setServiceId(long serviceId) {
        this.serviceId = serviceId;
    }

    @Column(name = "service_type", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    public AuthService getServiceType() {
        return serviceType;
    }

    public void setServiceType(AuthService serviceType) {
        this.serviceType = serviceType;
    }

    @Basic
    @Column(name = "service_name", nullable = false)
    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    @Basic
    @Column(name = "client_id", nullable = false)
    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Basic
    @Column(name = "secret_key", nullable = false)
    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    @OneToMany(mappedBy = "socialService", cascade = CascadeType.ALL)
    public List<SocialProfile> getSocialProfiles() {
        return socialProfiles;
    }

    public void setSocialProfiles(List<SocialProfile> socialProfiles) {
        this.socialProfiles = socialProfiles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof SocialService)) return false;

        SocialService that = (SocialService) o;

        return new EqualsBuilder()
                .append(getServiceId(), that.getServiceId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getServiceId())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("serviceId", getServiceId())
                .append("serviceName", getServiceName())
                .append("clientId", getClientId())
                .append("secretKey", getSecretKey())
                .toString();
    }
}
