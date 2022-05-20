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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProfileRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private String accessToken = null;

    @BeforeAll
    public void setUp() throws Exception {
        /* If this fails, look first at LoginRestControllerTest for information */
        String contentAsString = mockMvc.perform(
                post("/api/v1/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(USERNAME_PASSWORD_REST_JSON_BODY)
        ).andReturn().getResponse().getContentAsString();

        AccessTokenResponseEntity accessTokenResponseEntity = new ObjectMapper().readValue(contentAsString, AccessTokenResponseEntity.class);
        accessToken = accessTokenResponseEntity.getAccessToken();
    }

    @Test
    @Order(1)
    public void testShowProfileDataAsRawData() throws Exception {
        ResultActions perform = mockMvc.perform(
                get("/api/v1/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, accessToken)
        );

        String contentAsString = perform.andReturn().getResponse().getContentAsString();
        Assertions.assertTrue(contentAsString.length() > 0);
    }

    @Test
    @Order(2)
    public void testShowProfileDataAsJWT() throws Exception {
        ResultActions perform = mockMvc.perform(
                get("/api/v1/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("jwt", String.valueOf(true))
                        .header(HttpHeaders.AUTHORIZATION, accessToken)
        );

        String contentAsString = perform.andReturn().getResponse().getContentAsString();
        Assertions.assertTrue(contentAsString.length() > 0);

    }

}