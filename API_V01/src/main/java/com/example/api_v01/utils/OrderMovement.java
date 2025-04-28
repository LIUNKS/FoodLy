package com.example.api_v01.utils;

import com.example.api_v01.model.CustomerOrder;
import com.example.api_v01.model.OrderSet;
import com.example.api_v01.model.Product;

import java.util.Arrays;
import java.util.List;

public class OrderMovement {

    public static CustomerOrder createCustomerOrder (Integer stock, Product product, OrderSet order) {
        if(product.getStock().getCurrent_stock() >= stock) {
            return CustomerOrder.builder()
                    .product(product)
                    .count(stock)
                    .total_rice(product.getPrice() * stock)
                    .order(order)
                    .build();
        }
        System.out.println("Error: no se puede despachar mas productos que el stock actual");
        return null;
    }
    public static Double ContTotal (List<CustomerOrder> ListOrder) {
        Double total_rice = 0.0;
        for (CustomerOrder customerOrder : ListOrder) {
            total_rice += customerOrder.getTotal_rice();
        }
        return  total_rice;
    }
}
