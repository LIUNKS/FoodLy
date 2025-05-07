package com.example.api_v01.service.order_set_service;


import com.example.api_v01.dto.OrderSetDTO;
import com.example.api_v01.handler.NotFoundException;
import com.example.api_v01.model.OrderSet;
import com.example.api_v01.repository.OrderSetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class OrderSetServiceImp implements  OrderSetService {

    private final OrderSetRepository orderSetRepository;

    @Override
    public OrderSet saveOrderSet(UUID id_arching, OrderSetDTO orderSetDTO) throws NotFoundException {
        return null;
    }

    @Override
    public OrderSet getOrderSet(UUID id_orderSet) throws NotFoundException {
        return null;
    }

    @Override
    public OrderSet getOrderSetByCustomer(String Customer_name) throws NotFoundException {
        return null;
    }

    @Override
    public void deleteOrderSet(UUID id_orderSet) throws NotFoundException {

    }

    @Override
    public List<OrderSet> getOrderSets() {
        return List.of();
    }
}
