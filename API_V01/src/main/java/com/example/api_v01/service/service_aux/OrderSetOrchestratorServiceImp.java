package com.example.api_v01.service.service_aux;

import com.example.api_v01.dto.entityLike.CustomerOrderDTO;
import com.example.api_v01.dto.entityLike.OrderSetDTO;
import com.example.api_v01.dto.response.OrderSetWithListCustomerOrderDTO;
import com.example.api_v01.handler.BadRequestException;
import com.example.api_v01.handler.NotFoundException;
import com.example.api_v01.model.CustomerOrder;
import com.example.api_v01.model.OrderSet;
import com.example.api_v01.service.customer_order_service.CustomerOrderService;
import com.example.api_v01.service.order_set_service.OrderSetService;
import com.example.api_v01.utils.OrderSetMovement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderSetOrchestratorServiceImp implements OrderSetOrchestratorService {

    private final OrderSetService orderSetService;
    private final CustomerOrderService customerOrderService;

    //Sirve para guardar la lista junto con sus órdenes
    @Override
    public OrderSet saveCompleteOrderSet(UUID id_arching, OrderSetDTO orderSetDTO, List<CustomerOrderDTO> listCustomer) throws NotFoundException, BadRequestException {
        OrderSet orderSet = orderSetService.saveBaseOrderSet(id_arching, orderSetDTO);
        for (CustomerOrderDTO customerOrderDTO : listCustomer) {
            customerOrderService.saveCustomerOrder(
                    customerOrderDTO.getId_product(),
                    orderSet.getId_order_set(),
                    customerOrderDTO.getCount()
            );
        }
        Double totalAmount = customerOrderService.TotalAmountOrderSet(orderSet.getId_order_set());
        orderSet.setTotal_order(totalAmount);
        return orderSet;
    }

    @Override
    public OrderSetWithListCustomerOrderDTO getOrderSetDTO(UUID id_orderSet) throws NotFoundException {
        OrderSet orderSet = orderSetService.getOrderSet(id_orderSet);
        List<CustomerOrder>customerOrders=customerOrderService.listCustomerOrdersByOrderSet(id_orderSet);
        return OrderSetMovement.createOrderSetWithListCustomerOrderDTO(orderSet, customerOrders);
    }

}
