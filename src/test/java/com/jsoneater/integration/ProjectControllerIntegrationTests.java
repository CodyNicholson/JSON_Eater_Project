package com.jsoneater.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsoneater.constant.Constant;
import com.jsoneater.error.ErrorResponseModel;
import com.jsoneater.util.SpringCommandLineProfileResolver;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(resolver = SpringCommandLineProfileResolver.class)
public class ProjectControllerIntegrationTests {
    @Autowired
    TestRestTemplate testRestTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void eatJson_validJSON_200OkayWithJsonResponse() {
        String inputJson = "{\"name\": \"Pear yPhone 72\", \"category\": \"cellphone\", \"details\": {\"displayAspectRatio\": \"97:3\", \"audioConnector\": \"none\" } }";
        HttpEntity inputRequest = new HttpEntity(inputJson, null);
        ResponseEntity<String> response = testRestTemplate.exchange("/eat", HttpMethod.POST, inputRequest, String.class);

        assertNotNull(response.getBody());
    }

    @Test
    void eatJson_invalidInputJson_400BadRequest() throws JsonProcessingException {
        String badJson = "\"Pear yPhone 72\", \"category\": \"cellphone\", \"details\": {\"displayAspectRatio\": \"97:3\", \"audioConnector\": \"none\" } }";
        HttpEntity inputRequest = new HttpEntity(badJson, null);
        ResponseEntity<String> response = testRestTemplate.exchange("/eat", HttpMethod.POST, inputRequest, String.class);
        ErrorResponseModel errorResponse = this.objectMapper.readValue(response.getBody(), ErrorResponseModel.class);

        assertNotNull(response);
        assertNotNull(errorResponse.getDate());
        assertEquals(400, errorResponse.getStatusCode());
        assertEquals(Constant.REST_BAD_REQUEST, errorResponse.getRestErrorMessage());
        assertEquals(Constant.INVALID_JSON, errorResponse.getDetailedErrorMessage());
    }
}
