package com.viewnext.dapr.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.Map;

@RestController
@RequestMapping("/s2s")
@CrossOrigin(origins = "*")
public class s2sInvocationController {
    private static final Logger log = LoggerFactory.getLogger(s2sInvocationController.class);

    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    private static final String DAPR_HTTP_PORT = System.getenv().getOrDefault("DAPR_HTTP_PORT", "3500");

    @GetMapping("/enviaPedido")
    public ResponseEntity<String> enviaPedidoLocal() throws IOException, InterruptedException {
        log.info("-----------------------------------_-----------------------------ENVIANDO PEDIDOS.");
        String result = "";
        String dapr_url = "http://localhost:" + DAPR_HTTP_PORT + "/orders";
        for (int i=1; i<=5; i++) {
            int orderId = i;
            JSONObject obj = new JSONObject();
            obj.put("orderId", orderId);

            HttpRequest request = HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.ofString(obj.toString()))
                    .uri(URI.create(dapr_url))
                    .header("Content-Type", "application/json")
                    .header("dapr-app-id", "procesa-pedidos")
                    .build();

            try {
                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                log.info("-----------------------------------   Lanzada petición núm: " + i);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            result += "Order passed: "+ orderId + " ------ ";
            TimeUnit.MILLISECONDS.sleep(100);
        }
        ResponseEntity response = new ResponseEntity<>(result, HttpStatus.OK);
        return response;
    }
}
