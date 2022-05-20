package com.apereoclient.model.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "jwt")
public class JwtConfigEntity {

    private String issuer;
    private String key;
    private Integer validity;

}
