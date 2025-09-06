package com.alireza.engine.matching;

import com.alireza.engine.domain.Order;
import com.alireza.engine.domain.OrderType;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created By: Alireza Dolatabadi
 * Date: 9/7/2025
 * Time: 2:05 AM
 **/
@Component
public class MatchingEngine {

    private final NavigableMap<Double, Deque<Order>> buyerOrders = new TreeMap<>(Comparator.reverseOrder());
    private final NavigableMap<Double, Deque<Order>> sellerOrders = new TreeMap<>();
    private final ReentrantLock lock = new ReentrantLock();

    public MatchingEngine() {
        // Daemon thread continuously runs and waits for matching
        Thread matchingThread = new Thread(this::runMatchingLoop);
        matchingThread.setDaemon(true);
        matchingThread.start();
    }

    public void addOrder(Order order) {
        lock.lock();
        try {
            if (order.getType() == OrderType.BUY) {
                buyerOrders.computeIfAbsent(order.getPrice(), k -> new ArrayDeque<>()).addLast(order);
            } else {
                sellerOrders.computeIfAbsent(order.getPrice(), k -> new ArrayDeque<>()).addLast(order);
            }
        } finally {
            lock.unlock();
        }
    }

    private void runMatchingLoop() {
        while (true) {
            lock.lock();
            try {
                if (!buyerOrders.isEmpty() && !sellerOrders.isEmpty()) {
                    double highestBid = buyerOrders.firstKey();
                    double lowestAsk = sellerOrders.firstKey();

                    if (highestBid >= lowestAsk) {
                        Order buy = buyerOrders.get(highestBid).pollFirst();
                        Order sell = sellerOrders.get(lowestAsk).pollFirst();

                        int matchedQty = Math.min(buy.getQuantity(), sell.getQuantity());
                        buy.setQuantity(buy.getQuantity() - matchedQty);
                        sell.setQuantity(sell.getQuantity() - matchedQty);

                        System.out.println("Matched " + matchedQty + " units at price " + lowestAsk);

                        if (buy.getQuantity() > 0) buyerOrders.get(highestBid).addFirst(buy);
                        if (sell.getQuantity() > 0) sellerOrders.get(lowestAsk).addFirst(sell);

                        if (buyerOrders.get(highestBid).isEmpty()) buyerOrders.remove(highestBid);
                        if (sellerOrders.get(lowestAsk).isEmpty()) sellerOrders.remove(lowestAsk);
                    }
                }
            } finally {
                lock.unlock();
            }

            try {
                Thread.sleep(50); // small delay to reduce CPU usage
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
