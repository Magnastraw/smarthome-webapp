package com.netcracker.smarthome.business.auth.security;

import com.netcracker.smarthome.model.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails extends User implements UserDetails {
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(long id, String username, String password, String firstName, String lastName, String phoneNumber, boolean isTwoFactorAuth, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, firstName, lastName, phoneNumber, isTwoFactorAuth);
        setUserId(id);
        this.authorities = authorities;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public String getPassword() {
        return getEncrPassword();
    }

    public String getUsername() {
        return getEmail();
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return true;
    }
}
