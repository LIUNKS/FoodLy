package com.example.api_v01.service.service_aux;

import com.example.api_v01.dto.entityLike.CustomerOrderDTO;
import com.example.api_v01.dto.entityLike.OrderSetDTO;
import com.example.api_v01.dto.response.OrderSetWithListCustomerOrderDTO;
import com.example.api_v01.handler.BadRequestException;
import com.example.api_v01.handler.NotFoundException;
import com.example.api_v01.model.OrderSet;

import java.util.List;
import java.util.UUID;

public interface OrderSetOrchestratorService {
    OrderSet saveCompleteOrderSet(UUID id_arching, OrderSetDTO orderSetDTO, List<CustomerOrderDTO> listCustomer) throws NotFoundException, BadRequestException;
    OrderSetWithListCustomerOrderDTO getOrderSetDTO(UUID id_orderSet) throws NotFoundException;
}
