package com.netcracker.smarthome.business.auth.social;

import com.netcracker.smarthome.business.UserService;
import com.netcracker.smarthome.business.auth.RegistryService;
import com.netcracker.smarthome.business.auth.UserExistsException;
import com.netcracker.smarthome.model.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class SocialAuthenticator {
    private final RegistryService registryService;
    private final UserService userService;
    private final UserDetailsService userDetailsService;
    private final RememberMeServices rememberMeServices;

    @Autowired
    public SocialAuthenticator(RegistryService registryService, UserService userService, UserDetailsService userDetailsService, @Qualifier("rememberMeServices") RememberMeServices rememberMeServices) {
        this.registryService = registryService;
        this.userService = userService;
        this.userDetailsService = userDetailsService;
        this.rememberMeServices = rememberMeServices;
    }

    public void authenticate(SocialProfileInfo profileInfo) throws UserExistsException {
        User user = loadOrRegisterUser(profileInfo);
        saveToSecurityContext(user);
    }

    public void authenticateAndRemember(SocialProfileInfo profileInfo, HttpServletRequest request, HttpServletResponse response) throws UserExistsException {
        User user = loadOrRegisterUser(profileInfo);
        Authentication auth = saveToSecurityContext(user);
        rememberMeServices.loginSuccess(request, response, auth);
    }

    private User loadOrRegisterUser(SocialProfileInfo profileInfo) throws UserExistsException {
        User user = userService.getUserBySocialId(profileInfo.getSocialId(), profileInfo.getService());
        if (user == null)
            user = registryService.socialRegister(new User(profileInfo.getEmail(), "newpass123", profileInfo.getFirstName(), profileInfo.getLastName(), null, false), profileInfo.getSocialId(), profileInfo.getService());
        return user;
    }

    private Authentication saveToSecurityContext(User user) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        return auth;
    }
}
