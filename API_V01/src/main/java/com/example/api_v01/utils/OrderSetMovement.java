package com.example.api_v01.utils;

import com.example.api_v01.dto.entityLike.OrderSetDTO;
import com.example.api_v01.model.Arching;
import com.example.api_v01.model.OrderSet;

import java.time.LocalDate;
import java.time.LocalTime;

public class OrderSetMovement {
    //Se utiliza para crear el orderset
    public static OrderSet CreateOrderSet(Arching arching,OrderSetDTO orderSetDTO){
        return OrderSet.builder()
                .name_client(orderSetDTO.getName_client())
                .date_order(LocalDate.now())
                .time_order(LocalTime.now())
                .arching(arching)
                .build();
    }

}
