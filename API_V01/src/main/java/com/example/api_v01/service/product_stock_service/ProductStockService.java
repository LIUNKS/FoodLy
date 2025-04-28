package com.example.api_v01.service.product_stock_service;

import com.example.api_v01.dto.ProductDTO;
import com.example.api_v01.model.ProductStock;

import java.util.UUID;

public interface ProductStockService {
    ProductStock saveProductStock(ProductDTO productStock);
    void deleteProductStock(UUID id);
}
