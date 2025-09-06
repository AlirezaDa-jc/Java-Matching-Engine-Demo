package com.alireza.engine.domain;


import jakarta.persistence.*;
import lombok.*;

/**
 * Created By: Alireza Dolatabadi
 * Date: 9/7/2025
 * Time: 1:26 AM
 **/
@Entity(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderType type;

    private double price;

    private int quantity;
}

