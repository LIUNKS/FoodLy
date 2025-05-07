package com.example.api_v01.service.order_set_service;

import com.example.api_v01.dto.OrderSetDTO;
import com.example.api_v01.handler.NotFoundException;
import com.example.api_v01.model.OrderSet;

import java.util.List;
import java.util.UUID;

public interface OrderSetService {

    OrderSet saveOrderSet(UUID id_arching,OrderSetDTO orderSetDTO) throws NotFoundException;

    OrderSet getOrderSet(UUID id_orderSet) throws NotFoundException;

    OrderSet getOrderSetByCustomer(String Customer_name) throws NotFoundException;

    void deleteOrderSet(UUID id_orderSet) throws NotFoundException;

    List<OrderSet> getOrderSets() ;

}
