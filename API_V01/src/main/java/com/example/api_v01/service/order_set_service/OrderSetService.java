package com.example.api_v01.service.order_set_service;

import com.example.api_v01.dto.entityLike.CustomerOrderDTO;
import com.example.api_v01.dto.entityLike.OrderSetDTO;
import com.example.api_v01.handler.BadRequestException;
import com.example.api_v01.handler.NotFoundException;
import com.example.api_v01.model.OrderSet;

import java.util.List;
import java.util.UUID;

public interface OrderSetService {

    OrderSet saveBaseOrderSet(UUID id_arching, OrderSetDTO orderSetDTO) throws NotFoundException, BadRequestException;

    OrderSet getOrderSet(UUID id_orderSet) throws NotFoundException;

    OrderSet getOrderSetByCustomer(String Customer_name) throws NotFoundException;

    void deleteOrderSet(UUID id_orderSet) throws NotFoundException;

    List<OrderSet> getOrderSetsByArching(UUID id_arching) throws NotFoundException ;

    Double totalAmountArching(UUID id_arching);

}
