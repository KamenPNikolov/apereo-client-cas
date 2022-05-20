package com.apereoclient.web.client;

import com.apereoclient.model.config.CasConfigEntity;
import com.apereoclient.exeption.BadRequestException;
import com.apereoclient.model.data.AccessTokenResponseEntity;
import com.apereoclient.model.data.LoginBindingEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import static com.apereoclient.logging.LogMessage.*;

@Service
@Slf4j
public class ApereoWebClient {

    private final WebClient client;
    private final CasConfigEntity casConfigEntity;

    public ApereoWebClient(@Qualifier(value = "login-client") WebClient client, CasConfigEntity casConfigEntity) {
        this.client = client;
        this.casConfigEntity = casConfigEntity;
    }

    /**
     * Expects {@link LoginBindingEntity} which contains the Username and Password of the client.
     * According to <b>Resource Owner Credentials</b> flow from Apereo documentation it also requires
     * the Client ID and Client Secret of this service.<br>
     * After successful response, an <b>Access Token</b> is granted in either plain-text or JSON format.
     * The return of this method is {@link Flux} as to allow individual services
     * to decide how to handle the request - blocking or otherwise.
     * @param loginRequestBindingModel a {@link LoginBindingEntity}
     * @return {@link Flux} of {@link AccessTokenResponseEntity}
     */
    public Flux<AccessTokenResponseEntity> requestAccessTokenWithUsernameAndPassword(LoginBindingEntity loginRequestBindingModel) {
        return client
                .post()
                .uri(uriBuilder ->
                        uriBuilder
                                .path(casConfigEntity.getAccessTokenEndpoint())
                                .queryParam("grant_type", "password")
                                .queryParam("client_id", casConfigEntity.getClientId())
                                .queryParam("client_secret", casConfigEntity.getClientSecret())
                                .queryParam("username", loginRequestBindingModel.getUsername())
                                .queryParam("password", loginRequestBindingModel.getPassword())
                                .build()
                )
                .retrieve()
                .onStatus(HttpStatus::isError, err -> {
                    log.warn(String.format(BAD_RESPONSE_FROM_APEREO_SERVER, err.toString()));
                    throw new BadRequestException(String.format(BAD_RESPONSE_TO_USER, err.statusCode()));
                })
                .bodyToFlux(AccessTokenResponseEntity.class);
    }

    /**
     * Expects an <b>Access Token</b> with which a request is sent to Apereo Server. The response is
     * the user data in JSON format. The return of this method is {@link Flux} as to allow individual services
     * to decide how to handle the request - blocking or otherwise.
     * @param accessToken a token String
     * @return {@link Flux} of the authenticated user profile data.
     */
    public Flux<Object> requestClientProfile(String accessToken) {
        return client
                .post()
                .uri(uriBuilder ->
                        uriBuilder
                                .path(casConfigEntity.getProfileEndpoint())
                                .queryParam("access_token", accessToken)
                                .build()
                )
                .retrieve()
                .onStatus(HttpStatus::isError, err -> {
                    log.debug(String.format(BAD_RESPONSE_FROM_APEREO_SERVER, err.toString()));
                    throw new BadRequestException(String.format(BAD_RESPONSE_TO_USER, err.statusCode()));
                })
                .bodyToFlux(Object.class);
    }


}
