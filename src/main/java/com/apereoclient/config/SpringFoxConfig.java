package com.apereoclient.config;

import com.apereoclient.annotation.Developmental;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.function.Predicate;

/**
 * Swagger2 Configuration.
 * Currently, the swagger UI can be located at:<br>{@code http://localhost:8080/oidc-spring/swagger-ui/}  <br>
 * and the non-UI swagger docs at: <br>{@code http://localhost:8080/oidc-spring/v2/api-docs} <br>
 * <br>
 * Due to a bug in Springfox, which assumes that MVC’s path matching will use an Ant-based path matcher,
 * rather than a PathPattern-based matcher, a new property must be set in .yml file. <br>
 * {@code spring-mvc-pathmatch-matching-strategy: ant_path_matcher}<br>
 * PathPattern-based matching is already an option, and is the default option in SpringBoot 2.6. <br>
 *
 */
@Configuration
public class SpringFoxConfig {

    // Used in Spring security configuration to allow anonymous access to swagger ui and support elements
    public static final String[] SWAGGER_PATH =
            new String[]{"/swagger-ui/**", "/swagger-ui.html/**", "/configuration/**", "/swagger-resources/**", "/v2/api-docs"};

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .apis(Predicate.not(RequestHandlerSelectors.basePackage("org.springframework.boot")))
                .paths(PathSelectors.any())
                .build();
    }

}
