package com.example.api_v01.service.order_set_service;

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
    public OrderSet saveOrderSet(OrderSet orderSet) {
        return orderSetRepository.save(orderSet);
    }

    @Override
    public void deleteOrderSet(UUID orderSet) {
        orderSetRepository.deleteById(orderSet);
    }

    @Override
    public List<OrderSet> getOrderSets() {
        return orderSetRepository.findAll();
    }

    @Override
    public OrderSet getOrderSet(UUID id) {
        return orderSetRepository.findById(id).orElse(null);
    }
}
