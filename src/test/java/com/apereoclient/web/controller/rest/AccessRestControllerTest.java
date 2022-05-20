package com.apereoclient.web.controller.rest;

import com.apereoclient.model.data.AccessTokenResponseEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.apereoclient.StaticResourcesForTesting.USERNAME_PASSWORD_REST_JSON_BODY;
import static com.apereoclient.StaticResourcesForTesting.USER_GROUPS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AccessRestControllerTest {

    @Autowired
    private MockMvc mockMvc;


    private String accessToken = null;
    private String signedJWT = null;

    @BeforeAll
    public void setUp() throws Exception {
        /* If this fails, look first at LoginRestControllerTest for information */
        String token = mockMvc.perform(
                post("/api/v1/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(USERNAME_PASSWORD_REST_JSON_BODY)
        ).andReturn().getResponse().getContentAsString();

        AccessTokenResponseEntity accessTokenResponseEntity = new ObjectMapper().readValue(token, AccessTokenResponseEntity.class);
        accessToken = accessTokenResponseEntity.getAccessToken();

        signedJWT = mockMvc.perform(
                post("/api/v1/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("jwt", String.valueOf(true))
                        .content(USERNAME_PASSWORD_REST_JSON_BODY)
        ).andReturn().getResponse().getContentAsString();
    }

    @Test
    @Order(1)
    public void testAccessWithAccessToken() throws Exception {
        ResultActions perform = mockMvc.perform(
                get("/api/v1/access/accessToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, accessToken)
                        .param("groups", USER_GROUPS)
        );

        String response = perform.andReturn().getResponse().getContentAsString();

        Assertions.assertTrue(Boolean.parseBoolean(response));
    }

    @Test
    @Order(2)
    public void testAccessWithJWT() throws Exception {
        ResultActions perform = mockMvc.perform(
                get("/api/v1/access/jwt")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, signedJWT)
                        .param("groups", USER_GROUPS)
        );

        String response = perform.andReturn().getResponse().getContentAsString();

        Assertions.assertTrue(Boolean.parseBoolean(response));
    }


    @Test
    @Order(3)
    public void testAccessWithJWTReturnFalse() throws Exception {
        ResultActions perform = mockMvc.perform(
                get("/api/v1/access/jwt")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, signedJWT)
                        .param("groups", "RO=no_group_found")
        );

        String response = perform.andReturn().getResponse().getContentAsString();

        Assertions.assertFalse(Boolean.parseBoolean(response));
    }

}