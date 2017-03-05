package com.netcracker.smarthome.business.auth.oauth;

import com.netcracker.smarthome.business.UserService;
import com.netcracker.smarthome.business.auth.RegistryService;
import com.netcracker.smarthome.dal.repositories.SocialServiceRepository;
import com.netcracker.smarthome.model.entities.User;
import com.netcracker.smarthome.model.enums.AuthService;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Properties;

@Component
public class GoogleAuthenticator extends SocialServiceAuthenticator {
    @Autowired
    public GoogleAuthenticator(@Qualifier("oauthProperties") Properties configProperties, SocialServiceRepository repository, UserService userService, RegistryService registryService) {
        super(new OAuthTemplate(repository.getByServiceType(AuthService.GOOGLE),
                        configProperties.getProperty("google.scope"),
                        configProperties.getProperty("google.authPath"),
                        configProperties.getProperty("google.tokenPath"),
                        configProperties.getProperty("google.apiPath")),
                registryService, userService);
    }

    @Override
    public User authenticate(String code, String backRedirectURI) throws OAuthAuthorizationProcessingException {
        HttpClient httpClient = HttpClients.createDefault();
        try {
            String accessToken = requestAccessToken(httpClient, getAccessTokenRequestParams(code, backRedirectURI)).get("access_token");
            HashMap<String, String> userInfo = requestUserInfo(httpClient, accessToken, getUserInfoRequestURI(getUserInfoRequestParams()));
            return loadOrRegisterUser(userInfo);
        } catch (Exception e) {
            throw new OAuthAuthorizationProcessingException("Error during authentication via Google", e);
        }
    }

    @Override
    protected String getAccessTokenRequestParams(String code, String backRedirectURI) {
        return super.getAccessTokenRequestParams(code, backRedirectURI) + "&grant_type=authorization_code";
    }

    private String getUserInfoRequestParams() {
        return "fields=id,emails/value,name/familyName,name/givenName";
    }

    @Override
    protected HttpRequestBase buildAccessTokenRequest(String requestParams) throws Exception {
        HttpPost request = new HttpPost(getAccessTokenRequestURI(""));
        request.addHeader("Content-Type", "application/x-www-form-urlencoded");
        request.setEntity(new StringEntity(requestParams));
        return request;
    }

    @Override
    protected HashMap<String, String> parseUserInfoResponse(String responseBody) throws Exception {
        HashMap<String, String> responseFields = new HashMap<String, String>();
        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(responseBody);
        responseFields.put("email", ((JSONObject) ((JSONArray) obj.get("emails")).get(0)).get("value").toString());
        responseFields.put("first_name", (String) ((JSONObject) obj.get("name")).get("givenName"));
        responseFields.put("last_name", (String) ((JSONObject) obj.get("name")).get("familyName"));
        responseFields.put("id", obj.get("id").toString());
        return responseFields;
    }
}
