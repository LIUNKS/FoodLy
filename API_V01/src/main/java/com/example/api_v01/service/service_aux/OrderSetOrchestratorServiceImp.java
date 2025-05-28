package com.example.api_v01.service.service_aux;

import com.example.api_v01.dto.entityLike.CustomerOrderDTO;
import com.example.api_v01.dto.entityLike.OrderSetDTO;
import com.example.api_v01.dto.response.OrderSetResponseDTO;
import com.example.api_v01.dto.response.OrderSetWithListCustomerOrderDTO;
import com.example.api_v01.handler.BadRequestException;
import com.example.api_v01.handler.NotFoundException;
import com.example.api_v01.model.CustomerOrder;
import com.example.api_v01.model.OrderSet;
import com.example.api_v01.service.customer_order_service.CustomerOrderService;
import com.example.api_v01.service.order_set_service.OrderSetService;
import com.example.api_v01.utils.ExceptionMessage;
import com.example.api_v01.utils.OrderSetMovement;
import com.example.api_v01.utils.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderSetOrchestratorServiceImp implements OrderSetOrchestratorService, ExceptionMessage {

    private final OrderSetService orderSetService;
    private final CustomerOrderService customerOrderService;

    //Sirve para guardar la lista junto con sus órdenes
    @Override
    public Tuple<OrderSetResponseDTO, UUID> saveCompleteOrderSet(
            UUID id_arching,
            String name_client,
            List<CustomerOrderDTO> listCustomer
    ) throws NotFoundException, BadRequestException {

        if(listCustomer.isEmpty()){
            throw new BadRequestException(IS_EMPTY_LIST_ORDER_SET);
        }

        OrderSet orderSet = orderSetService.saveBaseOrderSet(id_arching, name_client);

        for (CustomerOrderDTO customerOrderDTO : listCustomer) {
            customerOrderService.saveCustomerOrder(
                    customerOrderDTO.getId_product(),
                    orderSet.getId_order_set(),
                    customerOrderDTO.getCount()
            );
        }

        Double totalAmount = customerOrderService.TotalAmountOrderSet(orderSet.getId_order_set());

        orderSet.setTotal_order(totalAmount);

        orderSet = orderSetService.save(orderSet);

        OrderSetResponseDTO responseDTO = OrderSetMovement.CreateOrderSetResponseDTO(orderSet);

        return new Tuple<>(responseDTO, orderSet.getId_order_set());
    }

    //Me devulver un OrderSet Mas completo por el su id
    @Override
    public OrderSetWithListCustomerOrderDTO getOrderSetDTO(UUID id_orderSet) throws NotFoundException {
        OrderSet orderSet = orderSetService.getOrderSet(id_orderSet);
        List<CustomerOrder>customerOrders=customerOrderService.listCustomerOrdersByOrderSet(id_orderSet);
        return OrderSetMovement.createOrderSetWithListCustomerOrderDTO(orderSet, customerOrders);
    }

    //Me devulver una lista de OrderSet Mas completo por el id del arqueo
    @Override
    public List<OrderSetDTO> findOrderSetByArching(UUID id_arching,int page) throws NotFoundException {
        return orderSetService.getOrderSetsByArching(id_arching,page);
    }

    //Me devulver una lista de OrderSet Mas completo por el nombre del cliente
    @Override
    public List<OrderSetDTO> findOrderSetByCustomer(String name,int page) throws NotFoundException {
        return orderSetService.getOrderSetByNameCustomer(name,page);
    }

}
