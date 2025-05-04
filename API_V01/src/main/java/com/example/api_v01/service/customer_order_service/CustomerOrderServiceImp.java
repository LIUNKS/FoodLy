package com.example.api_v01.service.customer_order_service;

import com.example.api_v01.model.CustomerOrder;
import com.example.api_v01.repository.CustomerOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerOrderServiceImp implements CustomerOrderService {

    private final CustomerOrderRepository customerOrderRepository;

    @Override
    public CustomerOrder saveCustomerOrder(CustomerOrder customerOrder) {
        return customerOrderRepository.save(customerOrder);
    }

    @Override
    public void deleteCustomerOrder(UUID id) {
        customerOrderRepository.deleteById(id);
    }

    @Override
    public List<CustomerOrder> getCustomerOrders(UUID id) {
        return customerOrderRepository.findAll();
    }

    @Override
    public CustomerOrder getCustomerOrder(UUID id) {
        return customerOrderRepository.findById(id).orElse(null);
    }

}
