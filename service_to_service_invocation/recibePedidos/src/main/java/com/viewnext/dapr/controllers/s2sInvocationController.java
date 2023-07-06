package com.viewnext.dapr.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.viewnext.dapr.models.Order;
import io.dapr.client.DaprClient;
import io.dapr.client.DaprClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class s2sInvocationController {
    private static final Logger log = LoggerFactory.getLogger(s2sInvocationController.class);

    @PostMapping("/orders")
    public ResponseEntity<String> recibePedido(@RequestBody Order orderFromBody)  {
        String result = "------------------------------- Order received: " + orderFromBody.getOrderId();
        log.info(result);
        ResponseEntity response = new ResponseEntity<>(result, HttpStatus.OK);
        return response;
    }


}
