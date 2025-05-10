package com.example.api_v01.service.order_set_service;


import com.example.api_v01.dto.entityLike.CustomerOrderDTO;
import com.example.api_v01.dto.entityLike.OrderSetDTO;
import com.example.api_v01.handler.BadRequestException;
import com.example.api_v01.handler.NotFoundException;
import com.example.api_v01.model.Arching;
import com.example.api_v01.model.OrderSet;
import com.example.api_v01.repository.OrderSetRepository;
import com.example.api_v01.service.arching_service.ArchingService;
import com.example.api_v01.service.customer_order_service.CustomerOrderService;
import com.example.api_v01.utils.OrderSetMovement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class OrderSetServiceImp implements  OrderSetService {

    private final OrderSetRepository orderSetRepository;
    private final ArchingService archingService;

    @Override
    public OrderSet saveBaseOrderSet(UUID id_arching, OrderSetDTO orderSetDTO) throws NotFoundException {
        Arching arching = archingService.getArchingById(id_arching);
        return orderSetRepository.save(OrderSetMovement.CreateOrderSet(arching, orderSetDTO));
    }

    //Poner una advertencia en el controlado encaso de que la devolucion sea 0.0 (La lista es vacia)
    @Override
    public Double totalAmountArching(UUID id_arching) {
        return orderSetRepository.findByArching(id_arching)
                .stream()
                .map(orderSet -> orderSet.getTotal_order())
                .reduce(0.0, Double::sum);
    }

    @Override
    public List<OrderSet> getOrderSetsByArching(UUID id_arching) throws NotFoundException {
        Arching arching = archingService.getArchingById(id_arching);
        return orderSetRepository.findByArching(arching.getId_arching());
    }

    @Override
    public OrderSet getOrderSet(UUID id_orderSet) throws NotFoundException {
        return orderSetRepository.findById(id_orderSet)
                .orElseThrow(()-> new NotFoundException("Lista de ordenes no encontrada"));
    }



    @Override
    public OrderSet getOrderSetByCustomer(String Customer_name) throws NotFoundException {
        return null;
    }

    //Debate sobre implementarla o no
    @Override
    public void deleteOrderSet(UUID id_orderSet) throws NotFoundException {}


}
