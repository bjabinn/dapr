package com.viewnext.dapr.controllers;

import com.viewnext.dapr.models.Order;
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

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/pubsub")
@CrossOrigin(origins = "*")
public class pubsubInvocationController {
    private static final Logger log = LoggerFactory.getLogger(pubsubInvocationController.class);

    @GetMapping("/enviaPedido")
    public ResponseEntity<String> enviaPedidoLocal() throws InterruptedException {
        log.info("-----------------------------------------------------------------------------PUB ENVIANDO PEDIDOS.");
        String result = "";

        String TOPIC_NAME = "orders";
        String PUBSUB_NAME = "orderpubsub";

        DaprClient client = new DaprClientBuilder().build();

        for (int i = 0; i < 5; i++) {
            int pedido = i + 1;
            String trickyStamp = LocalDateTime.now().getMinute() + "" + LocalDateTime.now().getSecond() + "" + pedido;
            int trickyStamp2 = Integer.parseInt(trickyStamp.replace(":",""));
            Order order = new Order(trickyStamp2);

            client.publishEvent(PUBSUB_NAME, TOPIC_NAME, order).block();
            log.info("-------------------------------------------------------------PUBLISHED ORDER ID: " + trickyStamp);
            result += "Published order id: " + trickyStamp + " ------ ";
            TimeUnit.MILLISECONDS.sleep(100);
        }

        ResponseEntity response = new ResponseEntity<>(result, HttpStatus.OK);
        return response;
    }
}
