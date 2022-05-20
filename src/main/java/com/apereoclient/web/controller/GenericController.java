package com.apereoclient.web.controller;

import com.apereoclient.exeption.BadAuthenticationResponseException;
import com.apereoclient.model.data.Oauth2UserDetailsEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.Map;

import static com.apereoclient.logging.LogMessage.OAUTH2_USER_MAPPING_FAILED;

@Slf4j
@Controller
@RequestMapping("/")
@ApiIgnore
public class GenericController {

    /**
     * The main controller used in the application. It serves as both index page and profile information page.
     * The default "/" is not secured by Spring security and allows anonymous access. The "/profile" path
     * is secured by Spring security and will execute the authentication flow redirect to Apereo CAS server.
     * Regardless of endpoint usage, the resulting data is presented by Thymeleaf templating engine.
     */
    @GetMapping(value = {"/profile", "/"})
    public String showProfile(Model model, OAuth2AuthenticationToken user) {

        if (user != null) {
            try {
                Map<String, Object> attributes = (Map<String, Object>) user.getPrincipal().getAttributes().get("attributes");
                Oauth2UserDetailsEntity mappedUser = new Oauth2UserDetailsEntity(attributes);

                model
                        .addAttribute("username", mappedUser.getUsername())
                        .addAttribute("groupsType", mappedUser.getGroupsType())
                        .addAttribute("idType", mappedUser.getIdType())
                        .addAttribute("regPcs", mappedUser.getRegPcs())
                        .addAttribute("groups", mappedUser.getGroups());

            } catch (Exception ex) {
                log.debug(ex.getMessage());
                throw new BadAuthenticationResponseException(OAUTH2_USER_MAPPING_FAILED);
            }
        }

        return "index";
    }

}
