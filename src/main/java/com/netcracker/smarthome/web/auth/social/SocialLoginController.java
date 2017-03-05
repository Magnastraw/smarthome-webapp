package com.netcracker.smarthome.web.auth.social;

import com.netcracker.smarthome.business.auth.oauth.FacebookAuthenticator;
import com.netcracker.smarthome.business.auth.oauth.GoogleAuthenticator;
import com.netcracker.smarthome.business.auth.oauth.SocialServiceAuthenticator;
import com.netcracker.smarthome.business.auth.oauth.VkAuthenticator;
import com.netcracker.smarthome.model.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;

@Controller
public class SocialLoginController {
    private static final Logger logger = LoggerFactory.getLogger(SocialLoginController.class);
    private final GoogleAuthenticator googleAuthenticator;
    private final FacebookAuthenticator facebookAuthenticator;
    private final VkAuthenticator vkAuthenticator;
    private final RememberMeServices rememberMeServices;
    private final UserDetailsService userDetailsService;

    @Autowired
    public SocialLoginController(GoogleAuthenticator googleAuthenticator, FacebookAuthenticator facebookAuthenticator, VkAuthenticator vkAuthenticator, @Qualifier("rememberMeServices") RememberMeServices rememberMeServices, UserDetailsService userDetailsService) {
        this.googleAuthenticator = googleAuthenticator;
        this.facebookAuthenticator = facebookAuthenticator;
        this.vkAuthenticator = vkAuthenticator;
        this.rememberMeServices = rememberMeServices;
        this.userDetailsService = userDetailsService;
    }

    @RequestMapping(value = "/google")
    public String googleLogin(@RequestParam("code") String code, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        login(code, googleAuthenticator, request, response);
        return "redirect:/";
    }

    @RequestMapping(value = "/facebook")
    public String facebookLogin(@RequestParam("code") String code, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        login(code, facebookAuthenticator, request, response);
        return "redirect:/";
    }

    @RequestMapping(value = "/vk")
    public String vkLogin(@RequestParam("code") String code, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        login(code, vkAuthenticator, request, response);
        return "redirect:/";
    }

    private void login(String code, SocialServiceAuthenticator authenticator, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            User user = authenticator.authenticate(code, buildPath(request));
            loginAfterSuccessAuth(user, request, response);
        } catch (Exception e) {
            logger.error("Authorization via OAuth failed", e);
            request.getSession(false).setAttribute("oauthExc", "Authorization failed!");
        }
    }

    private String buildPath(HttpServletRequest request) throws URISyntaxException {
        return String.format("%s://%s:%d%s",
                request.getScheme(),
                request.getServerName(),
                request.getServerPort(),
                request.getRequestURI());
    }

    private void loginAfterSuccessAuth(User user, HttpServletRequest request, HttpServletResponse response) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        if ((Boolean) request.getSession(false).getAttribute("remember"))
            rememberMeServices.loginSuccess(request, response, auth);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}
