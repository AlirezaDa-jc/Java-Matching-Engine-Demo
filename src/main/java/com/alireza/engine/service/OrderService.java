package com.alireza.engine.service;

import com.alireza.engine.domain.OrderType;
import com.alireza.engine.dto.OrderDTO;

import java.util.List;

/**
 * Created By: Alireza Dolatabadi
 * Date: 9/7/2025
 * Time: 1:30 AM
 **/
public interface OrderService {
    OrderDTO addOrder(OrderDTO order);

    List<OrderDTO> getOrders(OrderType type);
}
