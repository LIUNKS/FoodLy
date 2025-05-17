package com.example.api_v01.service.order_set_service;

import com.example.api_v01.dto.entityLike.CustomerOrderDTO;
import com.example.api_v01.dto.entityLike.OrderSetDTO;
import com.example.api_v01.dto.response.OrderSetWithListCustomerOrderDTO;
import com.example.api_v01.handler.BadRequestException;
import com.example.api_v01.handler.NotFoundException;
import com.example.api_v01.model.OrderSet;

import java.util.List;
import java.util.UUID;

public interface OrderSetService {

    OrderSet saveBaseOrderSet(UUID id_arching, OrderSetDTO orderSetDTO) throws NotFoundException, BadRequestException;

    Double totalAmountArching(UUID id_arching);

    void deleteOrderSet(UUID id_orderSet) throws NotFoundException;

    OrderSet getOrderSet(UUID id_orderSet) throws NotFoundException;

    List<OrderSetDTO> getOrderSetByNameCustomer(String name) throws NotFoundException;

    List<OrderSetDTO> getOrderSetsByArching(UUID id_arching) throws NotFoundException ;

}
