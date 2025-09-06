package com.alireza.engine.repository;

import com.alireza.engine.domain.Order;
import com.alireza.engine.domain.OrderType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created By: Alireza Dolatabadi
 * Date: 9/7/2025
 * Time: 1:23 AM
 **/
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByType(OrderType type);
}
