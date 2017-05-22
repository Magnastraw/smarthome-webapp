package com.netcracker.smarthome.business.auth.social;

import com.netcracker.smarthome.dal.repositories.SocialServiceRepository;
import com.netcracker.smarthome.model.entities.SocialService;
import com.netcracker.smarthome.model.enums.AuthService;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Properties;

@Component
public class VkClient implements SocialServiceClient {
    private OAuthClientTemplate vkTemplate;

    @Autowired
    public VkClient(@Qualifier("oauthProperties") Properties configProperties, SocialServiceRepository repository) {
        SocialService service = repository.getByServiceType(AuthService.VK);
        vkTemplate = new OAuthClientTemplate(
                service.getClientId(),
                service.getSecretKey(),
                configProperties.getProperty("vk.scope"),
                configProperties.getProperty("vk.authPath"),
                configProperties.getProperty("vk.tokenPath"),
                configProperties.getProperty("vk.infoApiPath"));
    }

    public String buildServiceRedirectUrl(String callbackUrl) {
        return String.format("%s?client_id=%s&redirect_uri=%s&response_type=code&scope=%s",
                vkTemplate.getAuthPath(),
                vkTemplate.getClientId(),
                callbackUrl,
                vkTemplate.getScope());
    }

    public SocialProfileInfo retrieveUserProfileInfo(String code, String callbackUrl) throws OAuthProcessingException {
        HttpClient httpClient = HttpClients.createDefault();
        SocialProfileInfo profileInfo;
        HashMap<String, String> tokenResponse;
        try {
            tokenResponse = getAccessToken(httpClient, code, callbackUrl);
            profileInfo = getUserInfo(httpClient, tokenResponse.get("access_token"), tokenResponse.get("id"));
        } catch (Exception e) {
            throw new OAuthProcessingException("Error retrieving user profile info from Vk via OAuth.", e);
        }
        profileInfo.setEmail(tokenResponse.get("email"));
        return profileInfo;
    }

    @Override
    public AuthService getIdentifier() {
        return AuthService.VK;
    }

    private HashMap<String, String> getAccessToken(HttpClient httpClient, String code, String callbackUrl) throws Exception {
        HttpResponse response = httpClient.execute(buildAccessTokenRequest(code, callbackUrl));
        StatusLine statusLine = response.getStatusLine();
        if (statusLine.getStatusCode() != 200)
            throw new UnsuccessfulRequestException("Error requesting access token.", statusLine.getStatusCode(), statusLine.getReasonPhrase());
        return parseAccessTokenResponse(EntityUtils.toString(response.getEntity()));
    }


    private HttpRequestBase buildAccessTokenRequest(String code, String backRedirectURI) throws Exception {
        String url = String.format("%s?code=%s&client_id=%s&client_secret=%s&redirect_uri=%s",
                vkTemplate.getTokenPath(),
                code,
                vkTemplate.getClientId(),
                vkTemplate.getClientSecret(),
                backRedirectURI);
        return new HttpGet(url);
    }

    private HashMap<String, String> parseAccessTokenResponse(String responseBody) throws Exception {
        HashMap<String, String> responseFields = new HashMap<String, String>();
        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(responseBody);
        responseFields.put("access_token", obj.get("access_token").toString());
        responseFields.put("email", (String) obj.get("email"));
        responseFields.put("id", obj.get("user_id").toString());
        return responseFields;
    }

    private SocialProfileInfo getUserInfo(HttpClient httpClient, String accessToken, String userId) throws Exception {
        HttpResponse response = httpClient.execute(buildUserInfoRequest(accessToken, userId));
        StatusLine statusLine = response.getStatusLine();
        if (statusLine.getStatusCode() != 200)
            throw new UnsuccessfulRequestException("Error requesting user info.", statusLine.getStatusCode(), statusLine.getReasonPhrase());
        return parseUserInfoResponse(EntityUtils.toString(response.getEntity()));
    }

    private HttpRequestBase buildUserInfoRequest(String accessToken, String userId) {
        String url = String.format("%s?access_token=%s&user_ids=%s&v=5.62", vkTemplate.getInfoApiPath(), accessToken, userId);
        return new HttpGet(url);
    }

    private SocialProfileInfo parseUserInfoResponse(String responseBody) throws Exception {
        SocialProfileInfo profileInfo = new SocialProfileInfo();
        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(responseBody);
        if (obj.get("error") != null)
            throw new UnsuccessfulRequestException("Error durlng request user info.", 400, "Bad request");
        JSONArray array = (JSONArray) obj.get("response");
        obj = (JSONObject) array.get(0);
        profileInfo.setLastName(obj.get("last_name").toString());
        profileInfo.setFirstName(obj.get("first_name").toString());
        profileInfo.setSocialId(obj.get("id").toString());
        return profileInfo;
    }
}
