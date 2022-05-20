package com.apereoclient.model.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class AccessTokenResponseEntity {

    private final String accessToken;
    private final String tokenType;
    private final Integer expiresIn;
    private final String scope;

    public AccessTokenResponseEntity(@JsonProperty(value = "access_token", required = true) String accessToken,
                                     @JsonProperty(value = "token_type", required = true) String tokenType,
                                     @JsonProperty(value = "expires_in", required = false) Integer expiresIn,
                                     @JsonProperty(value = "scope", required = false) String scope) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
        this.scope = scope;
    }
}
