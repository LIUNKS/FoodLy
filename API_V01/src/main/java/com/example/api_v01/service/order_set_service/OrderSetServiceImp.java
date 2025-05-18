package com.example.api_v01.service.order_set_service;


import com.example.api_v01.dto.entityLike.OrderSetDTO;
import com.example.api_v01.handler.NotFoundException;
import com.example.api_v01.model.Arching;
import com.example.api_v01.model.OrderSet;
import com.example.api_v01.repository.OrderSetRepository;
import com.example.api_v01.service.arching_service.ArchingService;
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

    //Lo utiliza para guardar el OrderSet junto con sus ordenes, es usado en un servicio aux
    //Se utiliza para un servicio no para controlador NO USAR EN CONTROLADOR
    @Override
    public OrderSet saveBaseOrderSet(UUID id_arching, String name_client) throws NotFoundException {
        Arching arching = archingService.getArchingById(id_arching);
        return orderSetRepository.save(OrderSetMovement.CreateOrderSet(arching, name_client));
    }

    //Se utiliza para un servicio no para un controlador NO USAR EN CONTROLADOR
    //Poner una advertencia en el controlado encaso de que la devolucion sea 0.0 (La lista es vacia)
    @Override
    public Double totalAmountArching(UUID id_arching) {
        return orderSetRepository.findByArching(id_arching)
                .stream()
                .map(orderSet -> orderSet.getTotal_order())
                .reduce(0.0, Double::sum);
    }

    //SE USA EN UN SERVICIO ,NO PARA CONTROLADOR
    @Override
    public OrderSet getOrderSet(UUID id_orderSet) throws NotFoundException {
        OrderSet orderSet = orderSetRepository.findById(id_orderSet)
                .orElseThrow(()-> new NotFoundException("Lista de ordenes no encontrada"));
        return orderSet;
    }

    //OrderSet sin su lista
    @Override
    public List<OrderSetDTO> getOrderSetsByArching(UUID id_arching) throws NotFoundException {
        List<OrderSet> listOrderSet =  orderSetRepository.findByArching(id_arching);
        return OrderSetMovement.CrearListOrderSetDTO(listOrderSet);
    }

    @Override
    public List<OrderSetDTO> getOrderSetByNameCustomer(String name) throws NotFoundException {
        List<OrderSet> list = orderSetRepository.findByNameClient(name);
        return OrderSetMovement.CrearListOrderSetDTO(list);
    }

}
