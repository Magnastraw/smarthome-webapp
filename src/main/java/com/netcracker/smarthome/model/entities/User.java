package com.netcracker.smarthome.model.entities;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "users", schema = "public", catalog = "smarthome_db")
public class User implements Serializable {
    private long userId;
    private String email;
    private String encrPassword;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private boolean isTwoFactorAuth;
    private List<GroupMember> groupsMembers;
    private List<Permission> managedPermissions;
    private List<Permission> permissions;
    private List<SmartHome> smartHomes;
    private List<SocialProfile> socialProfiles;

    public User() {
    }

    public User(String email, String encrPassword, String firstName, String lastName, String phoneNumber, boolean isTwoFactorAuth) {
        this.email = email.toLowerCase();
        this.encrPassword = encrPassword;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.isTwoFactorAuth = isTwoFactorAuth;
    }

    @Id
    @Column(name = "user_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "users_user_id_seq", allocationSize = 1)
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "email", nullable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email.toLowerCase();
    }

    @Basic
    @Column(name = "encr_password", nullable = false)
    public String getEncrPassword() {
        return encrPassword;
    }

    public void setEncrPassword(String encrPassword) {
        this.encrPassword = encrPassword;
    }

    @Basic
    @Column(name = "first_name", nullable = true)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "last_name", nullable = true)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "phone_number", nullable = true, length = 12)
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Basic
    @Column(name = "is_two_factor_auth", nullable = false)
    public boolean isTwoFactorAuth() {
        return isTwoFactorAuth;
    }

    public void setTwoFactorAuth(boolean twoFactorAuth) {
        isTwoFactorAuth = twoFactorAuth;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    public List<GroupMember> getGroupsMembers() {
        return groupsMembers;
    }

    public void setGroupsMembers(List<GroupMember> groupsMembers) {
        this.groupsMembers = groupsMembers;
    }

    @OneToMany(mappedBy = "admin")
    public List<Permission> getManagedPermissions() {
        return managedPermissions;
    }

    public void setManagedPermissions(List<Permission> managedPermissions) {
        this.managedPermissions = managedPermissions;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    public List<SmartHome> getSmartHomes() {
        for (SmartHome smartHome : smartHomes) {
            Hibernate.initialize(smartHome);
        }
        return smartHomes;
    }

    public void setSmartHomes(List<SmartHome> smartHome) {
        this.smartHomes = smartHome;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    public List<SocialProfile> getSocialProfiles() {
        return socialProfiles;
    }

    public void setSocialProfiles(List<SocialProfile> socialProfile) {
        this.socialProfiles = socialProfile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof User)) return false;

        User user = (User) o;

        return new EqualsBuilder()
                .append(getUserId(), user.getUserId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getUserId())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("userId", getUserId())
                .append("firstName", getFirstName())
                .append("lastName", getLastName())
                .append("phoneNumber", getPhoneNumber())
                .append("email", getEmail())
                .append("isTwoFactorAuth", isTwoFactorAuth())
                .toString();
    }
}
