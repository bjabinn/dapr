package com.viewnext.dapr.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dapr.client.DaprClient;
import io.dapr.client.DaprClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/secret")
@CrossOrigin(origins = "*")
public class secretController {
    private static final Logger log = LoggerFactory.getLogger(secretController.class);
    private static final ObjectMapper JSON_SERIALIZER = new ObjectMapper();
    private static final String SECRET_STORE_NAME = "localsecretstore";

    @GetMapping("/local")
    public ResponseEntity<String> getLocal() throws JsonProcessingException {
        DaprClient client = new DaprClientBuilder().build();
        Map<String, String> secret = client.getSecret(SECRET_STORE_NAME, "secret").block();
        String result = "Result: " + JSON_SERIALIZER.writeValueAsString(secret);
        ResponseEntity response = new ResponseEntity<>(result, HttpStatus.OK);
        return response;
    }

    @GetMapping("/azure")
    public ResponseEntity<String> getAzure() throws JsonProcessingException {
        DaprClient client = new DaprClientBuilder().build();
        Map<String, String> secret = client.getSecret(SECRET_STORE_NAME, "secret").block();
        String result = "Result: " + JSON_SERIALIZER.writeValueAsString(secret);
        ResponseEntity response = new ResponseEntity<>(result, HttpStatus.OK);
        return response;
    }
}
