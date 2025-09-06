package com.alireza.engine.controller;

import com.alireza.engine.domain.OrderType;
import com.alireza.engine.dto.OrderDTO;
import com.alireza.engine.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created By: Alireza Dolatabadi
 * Date: 9/7/2025
 * Time: 1:37 AM
 **/
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService service;

    @PostMapping
    public OrderDTO addOrder(@RequestBody OrderDTO order) {
        return service.addOrder(order);
    }

    @GetMapping("/{type}")
    public List<OrderDTO> getOrdersByType(@PathVariable OrderType type) {
        return service.getOrders(type);
    }
}
