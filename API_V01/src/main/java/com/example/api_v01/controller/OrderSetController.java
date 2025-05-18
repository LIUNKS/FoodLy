package com.example.api_v01.controller;

import com.example.api_v01.dto.entityLike.CustomerOrderDTO;
import com.example.api_v01.dto.response.CustomerOrderResponseDTO;
import com.example.api_v01.dto.response.OrderSetResponseDTO;
import com.example.api_v01.dto.response.SaveOrderSetListCustomerDTO;
import com.example.api_v01.dto.response.SuccessMessage;
import com.example.api_v01.handler.BadRequestException;
import com.example.api_v01.handler.NotFoundException;
import com.example.api_v01.service.service_aux.OrderSetOrchestratorService;
import com.example.api_v01.utils.Tuple;
import com.example.api_v01.utils.UriGeneric;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("orderSet")
@RequiredArgsConstructor
public class OrderSetController {

    private final OrderSetOrchestratorService orderSetOrchestratorService;

    @PostMapping("/{id_arching}")
    public ResponseEntity<?> saveOrderSet(@PathVariable UUID id_arching, SaveOrderSetListCustomerDTO NameClientWithListOrdes) throws NotFoundException, BadRequestException {
        String nameClient = NameClientWithListOrdes.getName_cliente();
        List<CustomerOrderDTO> orders = NameClientWithListOrdes.getOrders();
        Tuple<OrderSetResponseDTO, UUID>tuple = orderSetOrchestratorService.saveCompleteOrderSet(id_arching,nameClient,orders);
        URI location = UriGeneric.GenereURI("/{id_orderSet}",tuple.getSecond());
        SuccessMessage<?>successMessage=SuccessMessage.builder()
                .status(HttpStatus.OK)
                .message("Se guardo la lista de ordenes correctamente")
                .data(tuple.getFirst())
                .build();
        return ResponseEntity.created(location).body(successMessage);
    }

    @GetMapping("/{id_orderSet}")
    public ResponseEntity<?> getOrderSet(@PathVariable UUID id_orderSet) throws NotFoundException {
        SuccessMessage<?>successMessage=SuccessMessage.builder()
                .status(HttpStatus.OK)
                .message("Lista de cliente encontrada")
                .data(orderSetOrchestratorService.getOrderSetDTO(id_orderSet))
                .build();
        return ResponseEntity.ok(successMessage);
    }

    @GetMapping("/{id_arching}")
    public ResponseEntity<?> getListOrderSetByArching(@PathVariable UUID id_arching) throws NotFoundException {
        SuccessMessage<?>successMessage=SuccessMessage.builder()
                .status(HttpStatus.OK)
                .message("Lista de cliente encontrada")
                .data(orderSetOrchestratorService.findOrderSetByArching(id_arching))
                .build();
        return ResponseEntity.ok(successMessage);
    }

    @GetMapping("/{name_client}")
    public ResponseEntity<?> getListOrderSetByCustomer(@PathVariable String name_client) throws NotFoundException {
        SuccessMessage<?>successMessage=SuccessMessage.builder()
                .status(HttpStatus.OK)
                .message("Lista de cliente encontrada")
                .data(orderSetOrchestratorService.findOrderSetByCustomer(name_client))
                .build();
        return ResponseEntity.ok(successMessage);
    }

}
