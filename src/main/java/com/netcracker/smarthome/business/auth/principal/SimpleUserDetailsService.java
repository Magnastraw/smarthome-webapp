package com.netcracker.smarthome.business.auth.principal;

import com.netcracker.smarthome.dal.repositories.UserRepository;
import com.netcracker.smarthome.model.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;

public class SimpleUserDetailsService implements UserDetailsService {
    private final UserRepository repository;

    @Autowired
    public SimpleUserDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.getByEmail(email.toLowerCase());
        if (user == null)
            throw new UsernameNotFoundException(String.format("User with email '%s' not found!", email));
        return buildUserDetails(user);
    }

    private Collection<GrantedAuthority> buildAuthorities() {
        ArrayList<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return authorities;
    }

    private SimpleUserDetails buildUserDetails(User user) {
        return new SimpleUserDetails(
                user.getUserId(),
                user.getEmail(),
                user.getEncrPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhoneNumber(),
                user.isTwoFactorAuth(),
                user.getPreferChannel(),
                buildAuthorities());
    }
}
