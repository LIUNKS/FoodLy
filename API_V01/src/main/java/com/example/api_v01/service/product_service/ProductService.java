package com.example.api_v01.service.product_service;

import com.example.api_v01.dto.ProductDTO;
import com.example.api_v01.dto.ProductStockDTO;
import com.example.api_v01.handler.BadRequestException;
import com.example.api_v01.handler.NotFoundException;
import com.example.api_v01.model.Product;

import java.util.List;
import java.util.UUID;


public interface ProductService {

    Product saveProduct(ProductDTO product);

    void deleteProduct(UUID id) throws NotFoundException;

    List<Product> getProducts();

    Product getProduct(UUID id) throws NotFoundException;

    Product updateProduct(UUID id, ProductDTO productDTO) throws NotFoundException;

    void product_stockDelete(UUID id_stock) throws NotFoundException;
}
