package com.apereoclient.service;

import com.apereoclient.model.data.AccessTokenResponseEntity;
import com.apereoclient.model.data.LoginBindingEntity;
import com.apereoclient.web.client.ApereoWebClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import static com.apereoclient.logging.LogMessage.*;

@Slf4j
@Service
public class LoginService {

    private final ApereoWebClient apereoWebClient;
    private final ProfileService profileService;

    public LoginService(ApereoWebClient apereoWebClient, ProfileService profileService) {
        this.apereoWebClient = apereoWebClient;
        this.profileService = profileService;
    }

    /**
     * Executes a login request on behalf of user/service by using username and password.
     * If no additional parameters are used - the response is a {@link String} if accessToken that should
     * be used for further requests.
     * Else, if a boolean parameter is passed as true, it will return a JWT response, containing the user data.
     * @param loginRequestBindingModel a wrapper entity for the username and password
     * @param jwt a boolean. If true, the return is JWT user data. If false, an accessToken
     * @return a {@link AccessTokenResponseEntity} wrapping the accessToken data or a {@link String} of JWT user data.
     */
    public Object attemptLogin(LoginBindingEntity loginRequestBindingModel, boolean jwt) {
        log.debug(String.format(LOGIN_EVENT, loginRequestBindingModel.getUsername()));

        Flux<AccessTokenResponseEntity> response = apereoWebClient.requestAccessTokenWithUsernameAndPassword(loginRequestBindingModel);
        AccessTokenResponseEntity accessTokenResponseEntity = response.blockFirst();

        if (jwt) return profileService.showProfileWithAccessToken(accessTokenResponseEntity.getAccessToken(), true);

        return accessTokenResponseEntity;
    }



}
