package com.alireza.engine.mapper;

import com.alireza.engine.domain.Order;
import com.alireza.engine.dto.OrderDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Created By: Alireza Dolatabadi
 * Date: 9/7/2025
 * Time: 1:39 AM
 **/
@Mapper
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    OrderDTO toDTO(Order order);

    Order toEntity(OrderDTO orderDTO);

    List<OrderDTO> toList(List<Order> byType);
}
