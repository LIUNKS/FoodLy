package com.example.api_v01.service.customer_order_service;

import com.example.api_v01.handler.BadRequestException;
import com.example.api_v01.handler.NotFoundException;
import com.example.api_v01.model.CustomerOrder;
import com.example.api_v01.model.OrderSet;
import com.example.api_v01.model.Product;
import com.example.api_v01.repository.CustomerOrderRepository;
import com.example.api_v01.repository.ProductRepository;
import com.example.api_v01.service.order_set_service.OrderSetService;
import com.example.api_v01.service.product_service.ProductService;
import com.example.api_v01.service.product_stock_service.ProductStockService;
import com.example.api_v01.utils.CustomerOrderMovement;
import com.example.api_v01.utils.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerOrderServiceImp implements CustomerOrderService {

    private final CustomerOrderRepository customerOrderRepository;

    private final OrderSetService orderSetService;

    private final ProductRepository productRepository;

    private final ProductService productService;

    private final ProductStockService productStockService;

    //Servico para crear el customerOrder

    @Override
    public CustomerOrder IncrementStockCustomerOrder(UUID id_CustomerOrder, Integer count) throws BadRequestException {
        return null;
    }

    @Override
    public CustomerOrder AssignCustomerOrderAOderSET(UUID id_CustomerOrder, UUID id_OrderSet) throws NotFoundException, BadRequestException {
        return null;
    }

    //Se usar para pruebas
    @Override
    public CustomerOrder saveCustomerOrder(UUID id_product, Integer count) throws NotFoundException, BadRequestException {

        Product product = productService.getProduct(id_product);

        CustomerOrder customerOrder = CustomerOrderMovement.createCustomerOrder(product,count);

        if(customerOrder==null){
            throw new BadRequestException("No se puede quitar mas del stock actual del producto");
        }

        productStockService.discountStockById(product.getStock().getId_product_stock(),count);

        return customerOrderRepository.save(customerOrder);
    }

    //Preliminar de agregar (Aun no esta en uso)
    @Override
    public CustomerOrder saveCustomerOrder(UUID id_product,UUID id_orderSet,Integer count) throws NotFoundException, BadRequestException {
        Product product = productService.getProduct(id_product);
        OrderSet orderSet = orderSetService.getOrderSet(id_orderSet);
        CustomerOrder customerOrder = CustomerOrderMovement.createCustomerOrder(orderSet,product,count);
        if(customerOrder==null){
            throw new BadRequestException("No se puede quitar mas del stock actual del producto");
        }
        productStockService.discountStockById(product.getStock().getId_product_stock(),count);
        return customerOrderRepository.save(customerOrder);
    }

    //Servicio que elimina el costomerOrder
    @Override
    public void DeleteCustomerOrder(UUID id_CustomerOrder) throws NotFoundException {

        CustomerOrder customerOrder = customerOrderRepository.findById(id_CustomerOrder)
                .orElseThrow(()-> new NotFoundException("No se encontro la orden del cliente"));

        Product product = CustomerOrderMovement.rectificationStockCustomerOrder(customerOrder);

        productRepository.save(product);

        customerOrder.setProduct(null);

        customerOrder.setOrder(null);

        customerOrderRepository.delete(customerOrder);

    }

    @Override
    public CustomerOrder DiscountStockCustomerOrder(UUID id_CustomerOrder, Integer count) throws NotFoundException, BadRequestException {

        CustomerOrder customerOrder = customerOrderRepository.findById(id_CustomerOrder)
                .orElseThrow( () -> new NotFoundException("No se encontro la orden del cliente"));

        Tuple<CustomerOrder,Product>tuple = CustomerOrderMovement.discountStockCustomerOrder(customerOrder,count);

        if(tuple == null){
            throw new BadRequestException("No se pudo descontar la cantidad del producto en la orden del cliente");
        }

        Product product = tuple.getSecond();
        productRepository.save(product);

        return customerOrderRepository.save(tuple.getFirst());
    }

}
