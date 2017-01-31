package com.netcracker.smarthome.model.entities;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "users", schema = "public", catalog = "smarthome_db")
public class User {
    private long userId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String encrPassword;
    private boolean isTwoFactorAuth;
    private Collection<Event> events;
    private Collection<GroupMember> groupsMembers;
    private Collection<Metric> metrics;
    private Collection<Notification> notifications;
    private Collection<SocialProfile> socialProfiles;
    private Collection<UserPermission> adminPermissions;
    private Collection<UserPermission> userPermissions;

    public User() {
    }

    public User(String firstName, String lastName, String phoneNumber, String email, String encrPassword, boolean isTwoFactorAuth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.encrPassword = encrPassword;
        this.isTwoFactorAuth = isTwoFactorAuth;
    }

    @Id
    @Column(name = "user_id", nullable = false)
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "first_name", nullable = false, length = -1)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "last_name", nullable = false, length = -1)
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
    @Column(name = "email", nullable = false, length = -1)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "encr_password", nullable = false, length = -1)
    public String getEncrPassword() {
        return encrPassword;
    }

    public void setEncrPassword(String encrPassword) {
        this.encrPassword = encrPassword;
    }

    @Basic
    @Column(name = "is_two_factor_auth", nullable = false)
    public boolean isTwoFactorAuth() {
        return isTwoFactorAuth;
    }

    public void setTwoFactorAuth(boolean twoFactorAuth) {
        isTwoFactorAuth = twoFactorAuth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (userId != user.userId) return false;
        if (isTwoFactorAuth != user.isTwoFactorAuth) return false;
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
        if (phoneNumber != null ? !phoneNumber.equals(user.phoneNumber) : user.phoneNumber != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (encrPassword != null ? !encrPassword.equals(user.encrPassword) : user.encrPassword != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (userId ^ (userId >>> 32));
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (encrPassword != null ? encrPassword.hashCode() : 0);
        result = 31 * result + (isTwoFactorAuth ? 1 : 0);
        return result;
    }

    @OneToMany(mappedBy = "user")
    public Collection<Event> getEvents() {
        return events;
    }

    public void setEvents(Collection<Event> events) {
        this.events = events;
    }

    @OneToMany(mappedBy = "user")
    public Collection<GroupMember> getGroupsMembers() {
        return groupsMembers;
    }

    public void setGroupsMembers(Collection<GroupMember> groupsMembers) {
        this.groupsMembers = groupsMembers;
    }

    @OneToMany(mappedBy = "user")
    public Collection<Metric> getMetrics() {
        return metrics;
    }

    public void setMetrics(Collection<Metric> metrics) {
        this.metrics = metrics;
    }

    @OneToMany(mappedBy = "user")
    public Collection<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(Collection<Notification> notifications) {
        this.notifications = notifications;
    }

    @OneToMany(mappedBy = "user")
    public Collection<SocialProfile> getSocialProfiles() {
        return socialProfiles;
    }

    public void setSocialProfiles(Collection<SocialProfile> socialProfiles) {
        this.socialProfiles = socialProfiles;
    }

    @OneToMany(mappedBy = "admin")
    public Collection<UserPermission> getAdminPermissions() {
        return adminPermissions;
    }

    public void setAdminPermissions(Collection<UserPermission> adminPermissions) {
        this.adminPermissions = adminPermissions;
    }

    @OneToMany(mappedBy = "user")
    public Collection<UserPermission> getUserPermissions() {
        return userPermissions;
    }

    public void setUserPermissions(Collection<UserPermission> userPermissions) {
        this.userPermissions = userPermissions;
    }
}
