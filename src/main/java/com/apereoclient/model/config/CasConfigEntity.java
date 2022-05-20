package com.apereoclient.model.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "webclient-config")
public class CasConfigEntity {

    private String apereoServerUrl;
    private String clientId;
    private String clientSecret;
    private String accessTokenEndpoint;
    private String profileEndpoint;

}
