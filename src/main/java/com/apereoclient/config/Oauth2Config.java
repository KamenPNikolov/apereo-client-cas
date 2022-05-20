package com.apereoclient.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.boot.autoconfigure.security.servlet.StaticResourceRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;

import static com.apereoclient.config.SpringFoxConfig.SWAGGER_PATH;

@Configuration
@EnableWebSecurity
public class Oauth2Config extends WebSecurityConfigurerAdapter {

    @Value("${spring.security.oauth2.client.provider.cas.log-out-uri}")
    private String logoutUrl;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                /* Disable csrf (Currently, due to swagger) */
                .csrf().disable()

                .authorizeRequests()

                /* Thymeleaf Static resources */
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()

                /* Permit API Access */
                .antMatchers("/api/v1/**", "/").permitAll()

                /* Swagger Permissions (after debugging it can be set to .denyAll() */
                .antMatchers(SWAGGER_PATH).permitAll()

                /* Forbid all else */
                .antMatchers("/**").authenticated()

                /* Log out */
                .and()
                .logout()
                .logoutSuccessHandler(logoutHandler())
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")

                /* Log in (Perhaps find a way to remove default login page)*/
                .and()
                .oauth2Login();
    }

    /**
     * In order to properly log out with "Single-sign out" flow, more than just an invalidation of the session must
     * be done. A redirect to the authentication provider is executed which then is redirected back to this service.
     * @return a {@link LogoutSuccessHandler} used in http log out configuration
     * @apiNote .logout().logoutSuccessHandler(logoutHandler())
     */
    private LogoutSuccessHandler logoutHandler() {
        return (request, response, authentication) -> response.sendRedirect(generateLogoutRedirectUrl(request));
    }

    /**
     * An url builder.
     * @param request a {@link HttpServletRequest}
     * @return a {@link String} of redirect url
     */
    private String generateLogoutRedirectUrl(HttpServletRequest request) {
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int portNumber = request.getServerPort();
        String contextPath = request.getContextPath();

        String followUpUrl = ((scheme.equals("http") && portNumber != 80) || (scheme.equals("https") && portNumber != 443))
                ? ":" + portNumber
                : "";

        return logoutUrl + "?service=" + scheme + "://" + serverName + followUpUrl + contextPath + "/";
    }


}
