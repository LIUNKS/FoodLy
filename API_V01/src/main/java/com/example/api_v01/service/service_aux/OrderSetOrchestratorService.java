package com.example.api_v01.service.service_aux;

import com.example.api_v01.dto.entityLike.CustomerOrderDTO;
import com.example.api_v01.dto.entityLike.OrderSetDTO;
import com.example.api_v01.handler.BadRequestException;
import com.example.api_v01.handler.NotFoundException;
import com.example.api_v01.model.Arching;
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
public class OrderSetOrchestratorService {

    private final OrderSetService orderSetService;
    private final CustomerOrderService customerOrderService;

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
}
