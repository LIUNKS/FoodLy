package com.example.api_v01.service.customer_order_service;

import com.example.api_v01.model.CustomerOrder;

import java.util.List;
import java.util.UUID;

public interface CustomerOrderService {
    CustomerOrder saveCustomerOrder(CustomerOrder customerOrder);
    void deleteCustomerOrder(UUID id);
    List<CustomerOrder> getCustomerOrders(UUID id);
    CustomerOrder getCustomerOrder(UUID id);
}
