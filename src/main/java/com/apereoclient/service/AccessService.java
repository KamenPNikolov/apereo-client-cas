package com.apereoclient.service;

import com.apereoclient.exeption.BadAuthenticationResponseException;
import com.apereoclient.model.data.JwtDataEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;

import static com.apereoclient.logging.LogMessage.JWT_TO_OBJECT_MAPPING_ERROR;

@Service
@Slf4j
public class AccessService {

    private final JwtService jwtService;
    private final ProfileService profileService;

    public AccessService(JwtService jwtService, ProfileService profileService) {
        this.jwtService = jwtService;
        this.profileService = profileService;
    }

    /**
     * Accepts accessToken and {@link String} of groups parameters.
     * The accessToken is used to request the user details from CAS server, after which
     * follow the @see.
     * @see #validateAccessWithJwt(String, String)
     *
     */
    public boolean validateAccessWithAccessToken(String accessToken, String groups) {
        String s = profileService.showProfileWithAccessToken(accessToken, true);
        return validateAccessWithJwt(s, groups);
    }

    /**
     * Accepts signed JWT token and {@link String} of user groups.
     * The JWT is first validated and decoded, after which the information is
     * mapped to {@link JwtDataEntity}. The user roles are extracted to a HashSet.
     * If any of the provided roles by the method parameter match at least one of
     * the user roles - this method will return true.
     * @param jwt a {@link String} of signed JWT token
     * @param groups a {@link String} of groups that are queried for access. They string must
     *               contain roles, separated by ",". Example: <i><code>DO=lorem,AP=ipsum</code></i>
     * @return true - if any of the groups in the request are contained as user roles
     */
    public boolean validateAccessWithJwt(String jwt, String groups) {

        Object o = jwtService.validateJwt(jwt);

        JwtDataEntity jwtDataEntity;
        try {
            jwtDataEntity = new ObjectMapper().convertValue(o, JwtDataEntity.class);
        } catch (Exception ex) {
            log.debug(JWT_TO_OBJECT_MAPPING_ERROR);
            throw new BadAuthenticationResponseException(JWT_TO_OBJECT_MAPPING_ERROR);
        }

        HashSet<String> userGroups = new HashSet<>();

        for (String group : jwtDataEntity.getGroups()) {
            userGroups.addAll(Arrays.asList(group.split(",")));
        }

        String[] requestedGroups = groups.split(",");

        for (String groupToFind : requestedGroups) {
            if (userGroups.contains(groupToFind)) return true;
        }
        return false;
    }
}
