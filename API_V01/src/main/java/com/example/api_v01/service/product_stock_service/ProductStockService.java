package com.example.api_v01.service.product_stock_service;


import com.example.api_v01.dto.ProductStockDTO;
import com.example.api_v01.handler.BadRequestException;
import com.example.api_v01.handler.NotFoundException;
import com.example.api_v01.model.ProductStock;

import java.util.List;
import java.util.UUID;

public interface ProductStockService {
    ProductStock saveProductStock(UUID id_product,ProductStockDTO productStockDTO) throws NotFoundException;
    ProductStock getProductStockById(UUID id) throws NotFoundException;
    List<ProductStock> getAllProductStock();
    void deleteProductStock(UUID id) throws NotFoundException;
    ProductStock cleanStockById(UUID id) throws NotFoundException;
    ProductStock increaseStockById(UUID id,Integer count) throws NotFoundException;
    ProductStock discountStockById(UUID id,Integer count) throws NotFoundException , BadRequestException;
    ProductStock updateStockById(UUID id,ProductStockDTO productStockDTO) throws NotFoundException , BadRequestException;
}
