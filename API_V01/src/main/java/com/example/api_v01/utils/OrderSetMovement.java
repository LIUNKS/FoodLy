package com.example.api_v01.utils;

import com.example.api_v01.dto.OrderSetDTO;
import com.example.api_v01.model.Arching;
import com.example.api_v01.model.OrderSet;

import java.time.LocalDate;

public class OrderSetMovement {
    public static OrderSet CreateOrderSet(OrderSetDTO orderSetDTO){
        return OrderSet.builder()
                .name_client(orderSetDTO.getName_client())
                .date_order(LocalDate.now())
                .time_order(null)
                .total_order(null)
                .arching(null)
                .build();
    }
}
