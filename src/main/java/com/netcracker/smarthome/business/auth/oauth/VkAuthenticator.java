package com.netcracker.smarthome.business.auth.oauth;

import com.netcracker.smarthome.business.services.UserService;
import com.netcracker.smarthome.business.services.RegistryService;
import com.netcracker.smarthome.dal.repositories.SocialServiceRepository;
import com.netcracker.smarthome.model.entities.User;
import com.netcracker.smarthome.model.enums.AuthService;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
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
public class VkAuthenticator extends SocialServiceAuthenticator {
    @Autowired
    public VkAuthenticator(@Qualifier("oauthProperties") Properties configProperties, SocialServiceRepository repository, RegistryService registryService, UserService userService) {
        super(new OAuthTemplate(repository.getByServiceType(AuthService.VK),
                        configProperties.getProperty("vk.scope"),
                        configProperties.getProperty("vk.authPath"),
                        configProperties.getProperty("vk.tokenPath"),
                        configProperties.getProperty("vk.apiPath")),
                registryService, userService);
    }

    @Override
    public User authenticate(String code, String backRedirectURI) throws OAuthAuthorizationProcessingException {
        HttpClient httpClient = HttpClients.createDefault();
        try {
            HashMap<String, String> info = requestAccessToken(httpClient, getAccessTokenRequestParams(code, backRedirectURI));
            String email = info.get("email");
            info = requestUserInfo(httpClient, info.get("access_token"), getUserInfoRequestURI(getUserInfoRequestParams(info.get("id"))));
            info.put("email", email);
            return loadOrRegisterUser(info);
        } catch (Exception e) {
            throw new OAuthAuthorizationProcessingException("Error during authentication via VK", e);
        }
    }

    private String getUserInfoRequestParams(String userId) {
        return String.format("user_ids=%s&v=5.62", userId);
    }

    @Override
    protected HttpRequestBase buildAccessTokenRequest(String requestParams) throws Exception {
        return new HttpGet(getAccessTokenRequestURI(requestParams));
    }

    @Override
    protected HashMap<String, String> parseAccessTokenResponse(String responseBody) throws Exception {
        HashMap<String, String> responseFields = super.parseAccessTokenResponse(responseBody);
        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(responseBody);
        responseFields.put("email", (String) obj.get("email"));
        responseFields.put("id", obj.get("user_id").toString());
        return responseFields;
    }

    @Override
    protected HashMap<String, String> parseUserInfoResponse(String responseBody) throws Exception {
        HashMap<String, String> responseFields = new HashMap<String, String>();
        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(responseBody);
        if (obj.get("error") != null)
            throw new UnsuccessfulRequestException("Error getting user info.", 400, "Bad request");
        JSONArray array = (JSONArray) obj.get("response");
        obj = (JSONObject) array.get(0);
        responseFields.put("last_name", obj.get("last_name").toString());
        responseFields.put("first_name", obj.get("first_name").toString());
        responseFields.put("id", obj.get("id").toString());
        return responseFields;
    }
}
