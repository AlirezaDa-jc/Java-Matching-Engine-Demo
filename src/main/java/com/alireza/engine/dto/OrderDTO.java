package com.alireza.engine.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created By: Alireza Dolatabadi
 * Date: 9/7/2025
 * Time: 1:38 AM
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {
    private String type;
    private double price;
    private int quantity;
}