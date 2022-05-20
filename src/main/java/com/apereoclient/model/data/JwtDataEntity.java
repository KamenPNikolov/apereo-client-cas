package com.apereoclient.model.data;

import com.apereoclient.exeption.BadAuthenticationResponseException;
import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This entity is used to map the data from JWT validated request.
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class JwtDataEntity {

    private String sub;
    private UserDataPayload payload;
    private Integer iat;
    private Integer exp;

    public String getSub() {
        return sub;
    }

    public UserDataPayload getPayload() {
        return payload;
    }

    public HashMap<String, Object> getAttributes() {
        return getPayload().getAttributes();
    }

    @SuppressWarnings("unchecked")
    public ArrayList<String> getGroups() {
        try {
            return (ArrayList<String>) getAttributes().get("groups");
        } catch (Exception ex) {
            throw new BadAuthenticationResponseException("Unable to parse groups!");
        }
    }

    public Integer getIat() {
        return iat;
    }

    public Integer getExp() {
        return exp;
    }

    @Data
    private static class UserDataPayload {
        private String service;
        private HashMap<String, Object> attributes;
        private String id;
        private String client_id;
    }
}
