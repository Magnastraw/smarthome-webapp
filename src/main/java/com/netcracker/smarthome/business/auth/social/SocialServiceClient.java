package com.netcracker.smarthome.business.auth.social;

import com.netcracker.smarthome.model.enums.AuthService;

/**
 * Defines methods to get user social profile information via OAuth using Authorization Code Flow scheme.
 */
public interface SocialServiceClient {
    /**
     * Build URL address to send authorization code request
     * (where user must be redirected to confirm access rights the first time).
     *
     * @param callbackUrl where request with authorization code must be sent back
     * @return URL
     */
    String buildServiceRedirectUrl(String callbackUrl);

    /**
     * Get user social profile information (id, email, etc).
     *
     * @param code        authorization code
     * @param callbackUrl URL which was specified as callback URL when obtaining authorization code
     * @return user social profile information
     */
    SocialProfileInfo retrieveUserProfileInfo(String code, String callbackUrl) throws OAuthProcessingException;

    AuthService getIdentifier();
}
