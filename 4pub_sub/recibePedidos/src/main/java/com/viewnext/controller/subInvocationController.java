package com.viewnext.controller;

import com.viewnext.models.Order;
import io.dapr.Topic;
import io.dapr.client.domain.CloudEvent;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Mono;

@RestController
public class subInvocationController {

    private static final Logger logger = LoggerFactory.getLogger(subInvocationController.class);

    @Topic(name = "orders", pubsubName = "orderpubsub")
    @PostMapping(path = "/orders", consumes = MediaType.ALL_VALUE)
    public Mono<ResponseEntity> recibePedidosLocal(@RequestBody(required = false) CloudEvent<Order> cloudEvent) {
        return Mono.fromSupplier(() -> {
            try {
                logger.info("Subscriber received: " + cloudEvent.getData().getOrderId());
                return ResponseEntity.ok("SUCCESS");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}

