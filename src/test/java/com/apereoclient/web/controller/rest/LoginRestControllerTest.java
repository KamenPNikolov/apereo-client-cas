package com.apereoclient.web.controller.rest;

import com.apereoclient.model.data.AccessTokenResponseEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.apereoclient.StaticResourcesForTesting.USERNAME_PASSWORD_REST_JSON_BODY;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LoginRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    public void testLoginForAccessToken() throws Exception {
        ResultActions perform = mockMvc.perform(
                post("/api/v1/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(USERNAME_PASSWORD_REST_JSON_BODY)
        );

        AccessTokenResponseEntity accessTokenResponseEntity = new ObjectMapper().readValue(perform.andReturn().getResponse().getContentAsString(), AccessTokenResponseEntity.class);

        Assertions.assertTrue(
                accessTokenResponseEntity.getAccessToken() != null
                        && accessTokenResponseEntity.getAccessToken().length() > 0
        );
    }

    @Test
    @Order(2)
    public void testLoginForJWT() throws Exception {
        ResultActions perform = mockMvc.perform(
                post("/api/v1/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("jwt", String.valueOf(true))
                        .content(USERNAME_PASSWORD_REST_JSON_BODY)
        );

        String contentAsString = perform.andReturn().getResponse().getContentAsString();
        Assertions.assertTrue(contentAsString.length() > 0);
    }
}