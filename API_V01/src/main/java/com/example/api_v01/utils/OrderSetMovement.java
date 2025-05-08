package com.example.api_v01.utils;

import com.example.api_v01.dto.OrderSetDTO;
import com.example.api_v01.model.Arching;
import com.example.api_v01.model.CustomerOrder;
import com.example.api_v01.model.OrderSet;
import org.aspectj.weaver.ast.Or;

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
                .total_order(null)
                .arching(arching)
                .build();
    }


    //Metodo axiliar para crear el orderSet
    public static OrderSet UpdateTotalOrder(OrderSet orderSet, List<CustomerOrder>ListCustomerOrder){
        Double totalOrder = ListCustomerOrder.stream()
                .map(customerOrder -> customerOrder.getTotal_rice())
                .reduce(0.0, (a, b) -> a + b);
        orderSet.setTotal_order(totalOrder);
        return orderSet;
    }
}
