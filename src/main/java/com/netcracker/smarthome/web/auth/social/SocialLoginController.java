package com.netcracker.smarthome.web.auth.social;

import com.netcracker.smarthome.business.auth.social.*;
import com.netcracker.smarthome.model.enums.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;

@Controller
@RequestMapping(value = "/social_login")
public class SocialLoginController {
    private static final Logger logger = LoggerFactory.getLogger(SocialLoginController.class);
    private final SocialAuthenticator authenticator;

    @Autowired
    public SocialLoginController(SocialAuthenticator authenticator) {
        this.authenticator = authenticator;
    }

    @RequestMapping(value = "/google")
    public String googleLogin(@RequestParam("code") String code, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        login(AuthService.GOOGLE, code, request, response);
        return "redirect:/";
    }

    @RequestMapping(value = "/facebook")
    public String facebookLogin(@RequestParam("code") String code, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        login(AuthService.FACEBOOK, code, request, response);
        return "redirect:/";
    }

    @RequestMapping(value = "/vk")
    public String vkLogin(@RequestParam("code") String code, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        login(AuthService.VK, code, request, response);
        return "redirect:/";
    }

    private void login(AuthService service, String code, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if ((Boolean) request.getSession(false).getAttribute("remember"))
                authenticator.authenticateAndRemember(service, code, buildPath(request), request, response);
            else
                authenticator.authenticate(service, code, buildPath(request));
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
}
