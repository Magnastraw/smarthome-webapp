package com.netcracker.smarthome.business.auth.social;

import com.netcracker.smarthome.dal.repositories.SocialServiceRepository;
import com.netcracker.smarthome.model.entities.SocialService;
import com.netcracker.smarthome.model.enums.AuthService;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

@Component
public class GoogleClient implements SocialServiceClient {
    private final OAuthClientTemplate googleTemplate;

    @Autowired
    public GoogleClient(@Qualifier("oauthProperties") Properties configProperties, SocialServiceRepository repository) {
        SocialService service = repository.getByServiceType(AuthService.GOOGLE);
        googleTemplate = new OAuthClientTemplate(
                service.getClientId(),
                service.getSecretKey(),
                configProperties.getProperty("google.scope"),
                configProperties.getProperty("google.authPath"),
                configProperties.getProperty("google.tokenPath"),
                configProperties.getProperty("google.infoApiPath"));
    }

    public String buildServiceRedirectUrl(String callbackUrl) {
        return String.format("%s?client_id=%s&redirect_uri=%s&response_type=code&scope=%s",
                googleTemplate.getAuthPath(),
                googleTemplate.getClientId(),
                callbackUrl,
                googleTemplate.getScope());
    }

    public SocialProfileInfo retrieveUserProfileInfo(String code, String callbackUrl) throws OAuthProcessingException {
        HttpClient httpClient = HttpClients.createDefault();
        SocialProfileInfo profileInfo;
        String token;
        try {
            token = getAccessToken(httpClient, code, callbackUrl);
            profileInfo = getUserInfo(httpClient, token);
        } catch (Exception e) {
            throw new OAuthProcessingException("Error retrieving user profile info from Google via OAuth.", e);
        }
        return profileInfo;
    }

    @Override
    public AuthService getIdentifier() {
        return AuthService.GOOGLE;
    }

    private String getAccessToken(HttpClient httpClient, String code, String callbackUrl) throws Exception {
        HttpResponse response = httpClient.execute(buildAccessTokenRequest(code, callbackUrl));
        StatusLine statusLine = response.getStatusLine();
        if (statusLine.getStatusCode() != 200)
            throw new UnsuccessfulRequestException("Error requesting access token.", statusLine.getStatusCode(), statusLine.getReasonPhrase());
        return parseAccessTokenResponse(EntityUtils.toString(response.getEntity()));
    }

    private HttpRequestBase buildAccessTokenRequest(String code, String callbackUrl) throws UnsupportedEncodingException {
        HttpPost request = new HttpPost(googleTemplate.getTokenPath());
        String requestParams = String.format("code=%s&client_id=%s&client_secret=%s&redirect_uri=%s&grant_type=authorization_code",
                code,
                googleTemplate.getClientId(),
                googleTemplate.getClientSecret(),
                callbackUrl);
        request.addHeader("Content-Type", "application/x-www-form-urlencoded");
        request.setEntity(new StringEntity(requestParams));
        return request;
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
        String url = googleTemplate.getInfoApiPath() + "?fields=id,emails/value,name/familyName,name/givenName";
        HttpGet request = new HttpGet(url);
        request.addHeader("Authorization", String.format("Bearer %s", accessToken));
        return request;
    }

    private SocialProfileInfo parseUserInfoResponse(String responseBody) throws Exception {
        SocialProfileInfo profileInfo = new SocialProfileInfo();
        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(responseBody);
        profileInfo.setEmail(((JSONObject) ((JSONArray) obj.get("emails")).get(0)).get("value").toString());
        profileInfo.setFirstName((String) ((JSONObject) obj.get("name")).get("givenName"));
        profileInfo.setLastName((String) ((JSONObject) obj.get("name")).get("familyName"));
        profileInfo.setSocialId(obj.get("id").toString());
        return profileInfo;
    }
}
