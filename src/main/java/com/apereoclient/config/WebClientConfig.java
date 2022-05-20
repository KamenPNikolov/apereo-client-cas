package com.apereoclient.config;

import com.apereoclient.model.config.CasConfigEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.client.WebClient;


@Configuration
public class WebClientConfig implements WebFluxConfigurer {

    private final CasConfigEntity casConfigEntity;

    public WebClientConfig(CasConfigEntity casConfigEntity) {
        this.casConfigEntity = casConfigEntity;
    }

    @Bean("login-client")
    public WebClient buildLoginWebClient() {
        return WebClient.builder()
                .baseUrl(casConfigEntity.getApereoServerUrl())
                .defaultCookie("cookieKey", "cookieValue")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }



}
