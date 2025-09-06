package com.alireza.engine;

import com.alireza.engine.dto.OrderDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderMatchingEngineApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String url(String path) {
        return "http://localhost:" + port + path;
    }

    @Test
    void contextLoads() {
        // default Spring Boot context load check
    }

    @Test
    void testAddAndGetOrders() {
        // Add BUY order
        OrderDTO buyOrder = new OrderDTO("BUY", 100.0, 10);
        ResponseEntity<OrderDTO> buyResponse = restTemplate.postForEntity(
                url("/api/orders"), buyOrder, OrderDTO.class);
        assertThat(buyResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Add SELL order
        OrderDTO sellOrder = new OrderDTO("SELL", 105.0, 5);
        ResponseEntity<OrderDTO> sellResponse = restTemplate.postForEntity(
                url("/api/orders"), sellOrder, OrderDTO.class);
        assertThat(sellResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Get BUY orders
        ResponseEntity<List> buyOrdersResponse =
                restTemplate.getForEntity(url("/api/orders/BUY"), List.class);
        assertThat(buyOrdersResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(buyOrdersResponse.getBody()).isNotEmpty();

        // Get SELL orders
        ResponseEntity<List> sellOrdersResponse =
                restTemplate.getForEntity(url("/api/orders/SELL"), List.class);
        assertThat(sellOrdersResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(sellOrdersResponse.getBody()).isNotEmpty();

        // Verify the first BUY order type
        Map<String, Object> buy = (Map<String, Object>) buyOrdersResponse.getBody().get(0);
        assertThat(buy.get("type")).isEqualTo("BUY");

        // Verify the first SELL order type
        Map<String, Object> sell = (Map<String, Object>) sellOrdersResponse.getBody().get(0);
        assertThat(sell.get("type")).isEqualTo("SELL");
    }
}
