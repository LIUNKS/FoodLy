package com.example.api_v01.service.product_stock_service;

import com.example.api_v01.dto.ProductDTO;
import com.example.api_v01.model.ProductStock;
import com.example.api_v01.repository.ProductStockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ProductStockServiceImp implements ProductStockService {

    private final ProductStockRepository productStockRepository;

    @Override
    public ProductStock saveProductStock(ProductDTO productStock) {
        ProductStock stock = ProductStock.builder()
                .ini_stock(productStock.getIni_stock())
                .current_stock(productStock.getCurrent_stock())
                .total_sold(productStock.getTotal_sold())
                .build();
        return productStockRepository.save(stock);
    }

    @Override
    public void deleteProductStock(UUID id) {
        productStockRepository.deleteById(id);
    }

}
