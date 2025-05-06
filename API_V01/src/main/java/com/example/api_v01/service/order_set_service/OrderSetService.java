package com.example.api_v01.service.order_set_service;

import com.example.api_v01.model.OrderSet;

import java.util.List;
import java.util.UUID;

public interface OrderSetService {
    OrderSet saveOrderSet(OrderSet orderSet);
    void deleteOrderSet(UUID orderSet);
    List<OrderSet> getOrderSets();
    OrderSet getOrderSet(UUID id);
}
