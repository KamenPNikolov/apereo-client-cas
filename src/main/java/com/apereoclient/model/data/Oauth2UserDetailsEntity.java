package com.apereoclient.model.data;

import io.jsonwebtoken.lang.Assert;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
public class Oauth2UserDetailsEntity {

    private final String username;
    private final String groupsType;
    private final String idType;
    private final String regPcs;
    private final ArrayList<String> groups;

    public Oauth2UserDetailsEntity(Map<String, Object> attributes) {
        this.username = (String) attributes.get("given_name");
        this.groupsType = (String) attributes.get("groups_type");
        this.idType = (String) attributes.get("id_type");
        this.regPcs = (String) attributes.get("reg_pcs");
        this.groups = (ArrayList<String>) attributes.get("groups");

        validate();
    }

    private void validate() {
        Assert.notNull(this.username);
        Assert.notNull(this.groupsType);
        Assert.notNull(this.idType);
        Assert.notNull(this.regPcs);
        Assert.notNull(this.groups);
        Assert.notEmpty(this.groups);
    }

}
