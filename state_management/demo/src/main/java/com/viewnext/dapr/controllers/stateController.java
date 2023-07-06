package com.viewnext.dapr.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.viewnext.dapr.models.Order;
import io.dapr.client.DaprClient;
import io.dapr.client.DaprClientBuilder;
import io.dapr.client.domain.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/state")
@CrossOrigin(origins = "*")
public class stateController {
    private static final Logger log = LoggerFactory.getLogger(stateController.class);
    private static final ObjectMapper JSON_SERIALIZER = new ObjectMapper();
    private static final String DAPR_STATE_STORE_LOCAL = "statestore";
    private static final String DAPR_STATE_STORE_AZURE = "statestore-azure";

    @GetMapping("/local")
    public ResponseEntity<String> getLocal() throws JsonProcessingException {
        log.info("-----------------------------------_----------------------------- CALLING LOCAL REDIS.");

        LocalDateTime instance = LocalDateTime.now();
        String globalResponse = instance + "\n";

        try (DaprClient client = new DaprClientBuilder().build()) {
            for (int i = 1; i <= 10; i++) {
                int orderId = i;
                Order order = new Order();
                order.setOrderId(orderId);

                // Guardamos estado dentro del store
                client.saveState(DAPR_STATE_STORE_LOCAL, String.valueOf(orderId), order).block();
                globalResponse += "Saving Order: " + order.getOrderId() + " ------ ";

                // Obtenemos el estado del store
                State<Order> response = client.getState(DAPR_STATE_STORE_LOCAL, String.valueOf(orderId), Order.class).block();
                globalResponse += "Getting Order: " + response.getValue().getOrderId() + " ------ ";

                // Borramos estado del store
                client.deleteState(DAPR_STATE_STORE_LOCAL, String.valueOf(orderId)).block();
                globalResponse += "Deleting Order: " + orderId  + " ------ ";
                TimeUnit.MILLISECONDS.sleep(100);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        LocalDateTime instance2 = LocalDateTime.now();
        globalResponse += instance2 + " ------ ";

        ResponseEntity response = new ResponseEntity<>(globalResponse, HttpStatus.OK);
        return response;
    }

    @GetMapping("/azure")
    public ResponseEntity<String> getAzure() throws JsonProcessingException {
        log.info("-----------------------------------_----------------------------- CALLING AZURE COSMOSDB.");

        LocalDateTime instance = LocalDateTime.now();
        String globalResponse = instance + "\n";

        try (DaprClient client = new DaprClientBuilder().build()) {
            for (int i = 1; i <= 10; i++) {
                int orderId = i;
                Order order = new Order();
                order.setOrderId(orderId);

                // Guardamos estado dentro del store
                client.saveState(DAPR_STATE_STORE_AZURE, String.valueOf(orderId), order).block();
                globalResponse += "Saving Order: " + order.getOrderId() + " ------ ";

                // Obtenemos el estado del store
                State<Order> response = client.getState(DAPR_STATE_STORE_AZURE, String.valueOf(orderId), Order.class).block();
                globalResponse += "Getting Order: " + response.getValue().getOrderId() + " ------ ";

                // Borramos estado del store
                client.deleteState(DAPR_STATE_STORE_AZURE, String.valueOf(orderId)).block();
                globalResponse += "Deleting Order: " + orderId  + " ------ ";
                TimeUnit.MILLISECONDS.sleep(100);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        LocalDateTime instance2 = LocalDateTime.now();
        globalResponse += instance2 + " ------ ";

        ResponseEntity response = new ResponseEntity<>(globalResponse, HttpStatus.OK);
        return response;
    }

}