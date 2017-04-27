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
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class FacebookClient implements SocialServiceClient {
    private final OAuthClientTemplate facebookTemplate;

    @Autowired
    public FacebookClient(@Qualifier("oauthProperties") Properties configProperties, SocialServiceRepository repository) {
        SocialService service = repository.getByServiceType(AuthService.FACEBOOK);
        facebookTemplate = new OAuthClientTemplate(
                service.getClientId(),
                service.getSecretKey(),
                configProperties.getProperty("facebook.scope"),
                configProperties.getProperty("facebook.authPath"),
                configProperties.getProperty("facebook.tokenPath"),
                configProperties.getProperty("facebook.infoApiPath"));
    }

    public String buildServiceRedirectUrl(String callbackUrl) {
        return String.format("%s?client_id=%s&redirect_uri=%s&response_type=code&scope=%s",
                facebookTemplate.getAuthPath(),
                facebookTemplate.getClientId(),
                callbackUrl,
                facebookTemplate.getScope());
    }

    public SocialProfileInfo retrieveUserProfileInfo(String code, String callbackUrl) throws OAuthProcessingException {
        HttpClient httpClient = HttpClients.createDefault();
        SocialProfileInfo profileInfo;
        String token;
        try {
            token = getAccessToken(httpClient, code, callbackUrl);
            profileInfo = getUserInfo(httpClient, token);
        } catch (Exception e) {
            throw new OAuthProcessingException("Error retrieving user profile info from Facebook via OAuth.", e);
        }
        return profileInfo;
    }

    private String getAccessToken(HttpClient httpClient, String code, String callbackUrl) throws Exception {
        HttpResponse response = httpClient.execute(buildAccessTokenRequest(code, callbackUrl));
        StatusLine statusLine = response.getStatusLine();
        if (statusLine.getStatusCode() != 200)
            throw new UnsuccessfulRequestException("Error requesting access token.", statusLine.getStatusCode(), statusLine.getReasonPhrase());
        return parseAccessTokenResponse(EntityUtils.toString(response.getEntity()));
    }

    private HttpRequestBase buildAccessTokenRequest(String code, String callbackUrl) {
        String url = String.format("%s?code=%s&client_id=%s&client_secret=%s&redirect_uri=%s",
                facebookTemplate.getTokenPath(),
                code,
                facebookTemplate.getClientId(),
                facebookTemplate.getClientSecret(),
                callbackUrl);
        return new HttpGet(url);
    }

    private String parseAccessTokenResponse(String responseBody) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(responseBody);
        return obj.get("access_token").toString();
    }

    private SocialProfileInfo getUserInfo(HttpClient httpClient, String accessToken) throws Exception {
        HttpResponse response = httpClient.execute(buildUserInfoRequest(accessToken));
        StatusLine statusLine = response.getStatusLine();
        if (statusLine.getStatusCode() != 200)
            throw new UnsuccessfulRequestException("Error requesting user info.", statusLine.getStatusCode(), statusLine.getReasonPhrase());
        return parseUserInfoResponse(EntityUtils.toString(response.getEntity()));
    }

    private HttpRequestBase buildUserInfoRequest(String accessToken) {
        String url = facebookTemplate.getInfoApiPath() + "?fields=id,email,first_name,last_name";
        HttpGet request = new HttpGet(url);
        request.addHeader("Authorization", String.format("Bearer %s", accessToken));
        return request;
    }

    private SocialProfileInfo parseUserInfoResponse(String responseBody) throws ParseException {
        SocialProfileInfo profileInfo = new SocialProfileInfo();
        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(responseBody);
        profileInfo.setFirstName((String) obj.get("first_name"));
        profileInfo.setLastName((String) obj.get("last_name"));
        profileInfo.setEmail((String) obj.get("email"));
        profileInfo.setSocialId(obj.get("id").toString());
        profileInfo.setService(AuthService.FACEBOOK);
        return profileInfo;
    }
}
