package com.viewnext.dapr.controllers;

import com.viewnext.dapr.models.Order;
import io.dapr.Topic;
import io.dapr.client.domain.CloudEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class pubsubInvocationController {
    private static final Logger log = LoggerFactory.getLogger(pubsubInvocationController.class);

    @Topic(name ="orders", pubsubName = "orderpubsub")
    @PostMapping(path = "/orders", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<String> recibePedido(@RequestBody(required = false) CloudEvent<Order> cloudEvent) {
        String result = "------------------------------- Order received: " + cloudEvent.getData().getOrderId();
        log.info(result);
        ResponseEntity response = new ResponseEntity<>(result, HttpStatus.OK);
        return response;
    }


}
