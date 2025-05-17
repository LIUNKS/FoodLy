package com.example.api_v01.utils;

import com.example.api_v01.dto.entityLike.OrderSetDTO;
import com.example.api_v01.dto.response.OrderSetWithListCustomerOrderDTO;
import com.example.api_v01.model.Arching;
import com.example.api_v01.model.CustomerOrder;
import com.example.api_v01.model.OrderSet;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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

    public static OrderSetDTO CreateOrderSetDTO(OrderSet orderSetDTO){
        return OrderSetDTO.builder()
                .id_order_set(orderSetDTO.getId_order_set())
                .name_client(orderSetDTO.getName_client())
                .total_order(orderSetDTO.getTotal_order())
                .build();
    }

    public static List<OrderSetDTO> CrearListOrderSetDTO(List<OrderSet> orderSetList){
        return orderSetList.stream()
                .map(OrderSetMovement::CreateOrderSetDTO)
                .toList();
    }

    public static OrderSetWithListCustomerOrderDTO createOrderSetWithListCustomerOrderDTO(OrderSet orderSet, List<CustomerOrder>listCustomerOrders){
        return null;
    }

}
