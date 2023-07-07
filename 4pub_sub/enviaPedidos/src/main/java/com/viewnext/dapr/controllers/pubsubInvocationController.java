package com.viewnext.dapr.controllers;

import com.viewnext.dapr.models.Order;
import io.dapr.client.DaprClient;
import io.dapr.client.DaprClientBuilder;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/pubsub")
@CrossOrigin(origins = "*")
public class pubsubInvocationController {
    private static final Logger log = LoggerFactory.getLogger(pubsubInvocationController.class);



    @GetMapping("/enviaPedido")
    public ResponseEntity<String> enviaPedidoLocal() throws IOException, InterruptedException {
        log.info("-----------------------------------_-----------------------------PUB SUB ENVIANDO PEDIDOS.");
        String result = "";

        String TOPIC_NAME = "orders";
        String PUBSUB_NAME = "orderpubsub";

        DaprClient client = new DaprClientBuilder().build();

        for (int i = 0; i <= 10; i++) {
            int orderId = i;
            Order order = new Order(orderId);

            client.publishEvent(PUBSUB_NAME, TOPIC_NAME, order).block();
            result += "Published order: " + order.getOrderId() + " ------ ";
            TimeUnit.MILLISECONDS.sleep(200);
        }


        ResponseEntity response = new ResponseEntity<>(result, HttpStatus.OK);
        return response;
    }
}
