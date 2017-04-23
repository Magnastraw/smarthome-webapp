package com.netcracker.smarthome.business.auth.social;

public class OAuthClientTemplate {
    private String clientId;
    private String clientSecret;
    private String scope;
    private String authPath;
    private String tokenPath;
    private String infoApiPath;

    public OAuthClientTemplate(String clientId, String clientSecret, String scope, String authPath, String tokenPath, String infoApiPath) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.scope = scope;
        this.authPath = authPath;
        this.tokenPath = tokenPath;
        this.infoApiPath = infoApiPath;
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

    public String getInfoApiPath() {
        return infoApiPath;
    }
}
