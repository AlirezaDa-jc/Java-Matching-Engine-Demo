package com.alireza.engine.service.impl;

import com.alireza.engine.domain.Order;
import com.alireza.engine.domain.OrderType;
import com.alireza.engine.dto.OrderDTO;
import com.alireza.engine.mapper.OrderMapper;
import com.alireza.engine.matching.MatchingEngine;
import com.alireza.engine.repository.OrderRepository;
import com.alireza.engine.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created By: Alireza Dolatabadi
 * Date: 9/7/2025
 * Time: 1:31 AM
 **/
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository repository;

    @Override
    public OrderDTO addOrder(OrderDTO orderBody) {
        Order order = OrderMapper.INSTANCE.toEntity(orderBody);
        order = repository.save(order);
        return OrderMapper.INSTANCE.toDTO(order);
    }

    @Override
    public List<OrderDTO> getOrders(OrderType type) {
        return OrderMapper.INSTANCE.toList(repository.findByType(type));
    }
}
