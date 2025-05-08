package com.example.api_v01.service.customer_order_service;

import com.example.api_v01.handler.BadRequestException;
import com.example.api_v01.handler.NotFoundException;
import com.example.api_v01.model.CustomerOrder;

import java.util.List;
import java.util.UUID;

public interface CustomerOrderService {

    CustomerOrder IncrementStockCustomerOrder(UUID id_CustomerOrder ,Integer count) throws BadRequestException;
    CustomerOrder AssignCustomerOrderAOderSET(UUID id_CustomerOrder , UUID id_OrderSet) throws NotFoundException, BadRequestException;

    CustomerOrder saveCustomerOrder(UUID id_product,Integer count) throws NotFoundException, BadRequestException;

    CustomerOrder saveCustomerOrder(UUID id_product,UUID id_orderSet,Integer count) throws NotFoundException, BadRequestException;

    void DeleteCustomerOrder(UUID id_CustomerOrder) throws NotFoundException, BadRequestException;

    CustomerOrder DiscountStockCustomerOrder(UUID id_CustomerOrder,Integer count) throws NotFoundException, BadRequestException;

}
