package com.netcracker.smarthome.business.auth.oauth;

import com.netcracker.smarthome.business.services.UserService;
import com.netcracker.smarthome.business.services.RegistryService;
import com.netcracker.smarthome.business.auth.UserExistsException;
import com.netcracker.smarthome.model.entities.User;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.HashMap;

public abstract class SocialServiceAuthenticator {
    private final OAuthTemplate template;
    private final RegistryService registryService;
    private final UserService userService;

    public SocialServiceAuthenticator(OAuthTemplate template, RegistryService registryService, UserService userService) {
        this.template = template;
        this.registryService = registryService;
        this.userService = userService;
    }

    public abstract User authenticate(String code, String backRedirectURI) throws OAuthAuthorizationProcessingException;

    public String getServiceRedirectURI(String backRedirectURI) {
        return String.format("%s?client_id=%s&redirect_uri=%s&response_type=code&scope=%s",
                template.getAuthPath(),
                template.getClientId(),
                backRedirectURI,
                template.getScope());
    }

    protected String getAccessTokenRequestURI(String requestParams) {
        return String.format("%s?%s", template.getTokenPath(), requestParams);
    }

    protected String getUserInfoRequestURI(String requestParams) {
        return String.format("%s?%s", template.getApiPath(), requestParams);
    }

    protected String getAccessTokenRequestParams(String code, String backRedirectURI) {
        return String.format("code=%s&client_id=%s&client_secret=%s&redirect_uri=%s",
                code,
                template.getClientId(),
                template.getClientSecret(),
                backRedirectURI);
    }

    protected HashMap<String, String> requestAccessToken(HttpClient httpClient, String requestParams) throws Exception {
        HttpRequestBase request = buildAccessTokenRequest(requestParams);
        HttpResponse response = httpClient.execute(request);
        StatusLine statusLine = response.getStatusLine();
        if (statusLine.getStatusCode() != 200)
            throw new UnsuccessfulRequestException("Error getting access token.", statusLine.getStatusCode(), statusLine.getReasonPhrase());
        return parseAccessTokenResponse(EntityUtils.toString(response.getEntity()));
    }

    protected HashMap<String, String> requestUserInfo(HttpClient httpClient, String token, String requestURI) throws Exception {
        HttpRequestBase request = buildUserInfoRequest(token, requestURI);
        HttpResponse response = httpClient.execute(request);
        StatusLine statusLine = response.getStatusLine();
        if (statusLine.getStatusCode() != 200)
            throw new UnsuccessfulRequestException("Error getting user info.", statusLine.getStatusCode(), statusLine.getReasonPhrase());
        return parseUserInfoResponse(EntityUtils.toString(response.getEntity()));
    }

    protected abstract HttpRequestBase buildAccessTokenRequest(String requestParams) throws Exception;

    protected HashMap<String, String> parseAccessTokenResponse(String responseBody) throws Exception {
        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(responseBody);
        HashMap<String, String> responseFields = new HashMap<String, String>();
        responseFields.put("access_token", obj.get("access_token").toString());
        return responseFields;
    }

    protected HttpRequestBase buildUserInfoRequest(String token, String requestURI) throws Exception {
        HttpGet request = new HttpGet(requestURI);
        request.addHeader("Authorization", String.format("Bearer %s", token));
        return request;
    }

    protected abstract HashMap<String, String> parseUserInfoResponse(String responseBody) throws Exception;

    protected User loadOrRegisterUser(HashMap<String, String> userInfo) throws UserExistsException {
        User user = userService.getUserBySocialId(userInfo.get("id"), template.getService());
        if (user == null)
            user = registryService.socialRegister(new User(userInfo.get("email"), "newpass123", userInfo.get("first_name"), userInfo.get("last_name"), null, false), userInfo.get("id"), template.getService());
        return user;
    }
}
