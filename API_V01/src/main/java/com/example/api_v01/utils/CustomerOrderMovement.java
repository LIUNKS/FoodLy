package com.example.api_v01.utils;

import com.example.api_v01.model.CustomerOrder;
import com.example.api_v01.model.OrderSet;
import com.example.api_v01.model.Product;
import com.example.api_v01.model.ProductStock;

import java.util.List;

public class CustomerOrderMovement {
    //Preliminar de agregar (Aun no esta en uso)
    public static CustomerOrder createCustomerOrder(OrderSet orderSet,Product product, Integer count) {
        if(product.getStock().getCurrent_stock() >= count){
            return CustomerOrder.builder()
                    .product(product)
                    .order(orderSet)
                    .count(count)
                    .total_rice( product.getPrice() * count )
                    .build();
        }
        return null;
    }
}
