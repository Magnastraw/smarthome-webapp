package com.netcracker.smarthome.business.auth.oauth;

import com.netcracker.smarthome.model.entities.SocialService;
import com.netcracker.smarthome.model.enums.AuthService;

public class OAuthTemplate {
    private AuthService service;
    private String clientId;
    private String clientSecret;
    private String scope;
    private String authPath;
    private String tokenPath;
    private String apiPath;

    public OAuthTemplate(SocialService service, String scope, String authPath, String tokenPath, String apiPath) {
        this.service = service.getServiceType();
        this.clientId = service.getClientId();
        this.clientSecret = service.getSecretKey();
        this.scope = scope;
        this.authPath = authPath;
        this.tokenPath = tokenPath;
        this.apiPath = apiPath;
    }

    public AuthService getService() {
        return service;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getScope() {
        return scope;
    }

    public String getAuthPath() {
        return authPath;
    }

    public String getTokenPath() {
        return tokenPath;
    }

    public String getApiPath() {
        return apiPath;
    }
}
