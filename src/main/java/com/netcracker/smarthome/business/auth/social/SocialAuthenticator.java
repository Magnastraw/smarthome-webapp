package com.netcracker.smarthome.business.auth.social;

import com.netcracker.smarthome.ApplicationContextHolder;
import com.netcracker.smarthome.business.auth.UserExistsException;
import com.netcracker.smarthome.business.services.RegistryService;
import com.netcracker.smarthome.business.services.UserService;
import com.netcracker.smarthome.model.entities.User;
import com.netcracker.smarthome.model.enums.AuthService;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Component
public class SocialAuthenticator {
    private final RegistryService registryService;
    private final UserService userService;
    private final UserDetailsService userDetailsService;
    private final RememberMeServices rememberMeServices;

    private Map<AuthService, SocialServiceClient> serviceClients;

    @Autowired
    public SocialAuthenticator(RegistryService registryService, UserService userService, UserDetailsService userDetailsService, @Qualifier("rememberMeServices") RememberMeServices rememberMeServices) {
        this.registryService = registryService;
        this.userService = userService;
        this.userDetailsService = userDetailsService;
        this.rememberMeServices = rememberMeServices;
    }

    @PostConstruct
    private void loadClients() {
        serviceClients = new HashMap<>();
        Reflections reflections = new Reflections("com.netcracker.smarthome.business.auth.social");
        ApplicationContext context = ApplicationContextHolder.getApplicationContext();
        SocialServiceClient client;
        for (Class<? extends SocialServiceClient> clientClass : reflections.getSubTypesOf(SocialServiceClient.class)
                ) {
            client = context.getBean(clientClass);
            serviceClients.put(client.getIdentifier(), client);
        }
    }

    public void authenticate(AuthService service, String code, String callbackUrl) throws UserExistsException, OAuthProcessingException {
        SocialServiceClient serviceClient = serviceClients.get(service);
        SocialProfileInfo profileInfo = serviceClient.retrieveUserProfileInfo(code, callbackUrl);
        User user = loadOrRegisterUser(profileInfo, service);
        saveToSecurityContext(user);
    }

    public void authenticateAndRemember(AuthService service, String code, String callbackUrl, HttpServletRequest request, HttpServletResponse response) throws UserExistsException, OAuthProcessingException {
        SocialServiceClient serviceClient = serviceClients.get(service);
        SocialProfileInfo profileInfo = serviceClient.retrieveUserProfileInfo(code, callbackUrl);
        User user = loadOrRegisterUser(profileInfo, service);
        Authentication auth = saveToSecurityContext(user);
        rememberMeServices.loginSuccess(request, response, auth);
    }

    private User loadOrRegisterUser(SocialProfileInfo profileInfo, AuthService service) throws UserExistsException {
        User user = userService.getUserBySocialId(profileInfo.getSocialId(), service);
        if (user == null)
            user = registryService.socialRegister(new User(profileInfo.getEmail(), null, profileInfo.getFirstName(), profileInfo.getLastName(), null, false, null), profileInfo.getSocialId(), service);
        return user;
    }

    private Authentication saveToSecurityContext(User user) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        return auth;
    }
}
