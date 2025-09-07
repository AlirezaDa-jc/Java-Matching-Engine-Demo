package com.alireza.engine.matching;

import com.alireza.engine.domain.Order;
import com.alireza.engine.domain.OrderType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created By: Alireza Dolatabadi
 * Date: 9/7/2025
 * Time: 1:57 AM
 **/
class MatchingEngineTest {

    private MatchingEngine matchingEngine;

    @BeforeEach
    void setup() {
        matchingEngine = new MatchingEngine();
    }

    @Test
    void testSimpleMatch() throws InterruptedException {
        Order buyOrder = Order.builder()
                .type(OrderType.BUY)
                .price(100.0)
                .quantity(10)
                .build();

        Order sellOrder = Order.builder()
                .type(OrderType.SELL)
                .price(90.0)
                .quantity(10)
                .build();

        matchingEngine.addOrder(buyOrder);
        matchingEngine.addOrder(sellOrder);

        // Wait a little for the daemon thread to match
        TimeUnit.MILLISECONDS.sleep(200);

        // Both orders should be fully matched and removed
        assertEquals(0, buyOrder.getQuantity());
        assertEquals(0, sellOrder.getQuantity());
    }

    @Test
    void testPartialMatch() throws InterruptedException {
        Order buyOrder = Order.builder()
                .type(OrderType.BUY)
                .price(100.0)
                .quantity(15)
                .build();

        Order sellOrder = Order.builder()
                .type(OrderType.SELL)
                .price(90.0)
                .quantity(10)
                .build();

        matchingEngine.addOrder(buyOrder);
        matchingEngine.addOrder(sellOrder);

        TimeUnit.MILLISECONDS.sleep(200);

        // Buy order partially filled, sell order fully filled
        assertEquals(5, buyOrder.getQuantity());
        assertEquals(0, sellOrder.getQuantity());
    }

    @Test
    void testMultiplePriceLevels() throws InterruptedException {
        Order buy1 = Order.builder().type(OrderType.BUY).price(105.0).quantity(5).build();
        Order buy2 = Order.builder().type(OrderType.BUY).price(100.0).quantity(10).build();
        Order sell1 = Order.builder().type(OrderType.SELL).price(95.0).quantity(8).build();
        Order sell2 = Order.builder().type(OrderType.SELL).price(100.0).quantity(5).build();

        matchingEngine.addOrder(buy1);
        matchingEngine.addOrder(buy2);
        matchingEngine.addOrder(sell1);
        matchingEngine.addOrder(sell2);

        TimeUnit.MILLISECONDS.sleep(300);

        // Check remaining quantities after matching
        assertEquals(2, buy2.getQuantity()); // 10 - 8 matched with sell1
        assertEquals(0, buy1.getQuantity()); // fully matched with sell2
        assertEquals(0, sell1.getQuantity()); // fully matched
        assertEquals(0, sell2.getQuantity()); // fully matched
    }

    @Test
    void testNoMatchWhenPricesDoNotOverlap() throws InterruptedException {
        Order buy = Order.builder().type(OrderType.BUY).price(90.0).quantity(10).build();
        Order sell = Order.builder().type(OrderType.SELL).price(100.0).quantity(10).build();

        matchingEngine.addOrder(buy);
        matchingEngine.addOrder(sell);

        TimeUnit.MILLISECONDS.sleep(200);

        // Quantities should remain unchanged
        assertEquals(10, buy.getQuantity());
        assertEquals(10, sell.getQuantity());
    }

    @Test
    void testBenchMark() {
        Instant start = Instant.now();

        matchingEngine.setInTrade(true);
        for (int i = 0; i < 100000; i++) {
            matchingEngine.addOrder(new Order(1L, OrderType.BUY, 30000, 1));
            matchingEngine.addOrder(new Order(1L, OrderType.SELL, 30000, 1));
        }
        while(matchingEngine.isInTrade()){Thread.yield();}
        Instant end = Instant.now();
        Duration duration = Duration.between(start, end);

        System.out.println("Time to enqueue 100000 orders: " + duration.toMillis() + " ms");
    }
}
