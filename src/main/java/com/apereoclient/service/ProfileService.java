package com.apereoclient.service;

import com.apereoclient.exeption.BadAuthenticationResponseException;
import com.apereoclient.web.client.ApereoWebClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import static com.apereoclient.logging.LogMessage.BAD_DATA_FROM_APEREO_RESPONSE;
import static com.apereoclient.logging.LogMessage.GETTING_USER_DATA;

@Service
@Slf4j
public class ProfileService {

    private final ApereoWebClient apereoWebClient;
    private final JwtService jwtService;

    public ProfileService(ApereoWebClient apereoWebClient, JwtService jwtService) {
        this.apereoWebClient = apereoWebClient;
        this.jwtService = jwtService;
    }

    /**
     * Takes in an accessToken, requests user data from Apereo CAS server and returns a signed JWT.
     * @param accessToken a String
     * @return a String of JWT
     */
    public String showProfileWithAccessToken(String accessToken, boolean jwt) {
        log.debug(GETTING_USER_DATA);

        Flux<Object> responseEntity = apereoWebClient.requestClientProfile(accessToken);
        Object responseObject = responseEntity.blockFirst();

        if (responseObject == null) throw new BadAuthenticationResponseException(BAD_DATA_FROM_APEREO_RESPONSE);
        if (jwt) return jwtService.createAndSignJwt(responseObject);
        return responseObject.toString();
    }

}
