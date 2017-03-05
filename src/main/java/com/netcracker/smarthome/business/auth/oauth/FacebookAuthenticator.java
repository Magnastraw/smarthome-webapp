package com.netcracker.smarthome.business.auth.oauth;

import com.netcracker.smarthome.business.UserService;
import com.netcracker.smarthome.business.auth.RegistryService;
import com.netcracker.smarthome.dal.repositories.SocialServiceRepository;
import com.netcracker.smarthome.model.entities.User;
import com.netcracker.smarthome.model.enums.AuthService;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Properties;

@Component
public class FacebookAuthenticator extends SocialServiceAuthenticator {
    @Autowired
    public FacebookAuthenticator(@Qualifier("oauthProperties") Properties configProperties, SocialServiceRepository repository, RegistryService registryService, UserService userService) {
        super(new OAuthTemplate(repository.getByServiceType(AuthService.FACEBOOK),
                        configProperties.getProperty("facebook.scope"),
                        configProperties.getProperty("facebook.authPath"),
                        configProperties.getProperty("facebook.tokenPath"),
                        configProperties.getProperty("facebook.apiPath")),
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
            throw new OAuthAuthorizationProcessingException("Error during authentication via Facebook", e);
        }
    }

    private String getUserInfoRequestParams() {
        return "fields=id,email,first_name,last_name";
    }

    @Override
    protected HttpRequestBase buildAccessTokenRequest(String requestParams) throws Exception {
        return new HttpGet(getAccessTokenRequestURI(requestParams));
    }

    @Override
    protected HashMap<String, String> parseUserInfoResponse(String responseBody) throws Exception {
        HashMap<String, String> responseFields = new HashMap<String, String>();
        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(responseBody);
        responseFields.put("first_name", (String) obj.get("first_name"));
        responseFields.put("last_name", (String) obj.get("last_name"));
        responseFields.put("email", (String) obj.get("email"));
        responseFields.put("id", obj.get("id").toString());
        return responseFields;
    }
}
