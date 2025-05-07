package com.example.api_v01.utils;

import com.example.api_v01.dto.ProductDTO;
import com.example.api_v01.model.Product;
import com.example.api_v01.model.ProductStock;


public class ProductMovement {
    public static Product createProductAndStock (ProductDTO productDTO) {
        ProductStock stock = ProductStock.builder()
                .ini_stock(0)
                .current_stock(0)
                .total_sold(0)
                .build();
        return  Product.builder()
                .name_product(productDTO.getName_product())
                .price(productDTO.getPrice())
                .additional_observation(productDTO.getAdditional_observation())
                .category(productDTO.getCategory())
                .stock(stock)
                .admin(null)
                .build();
    }

    public static Product createProduct(ProductDTO productDTO) {
        return  Product.builder()
                .name_product(productDTO.getName_product())
                .price(productDTO.getPrice())
                .additional_observation(productDTO.getAdditional_observation())
                .category(productDTO.getCategory())
                .admin(null)
                .build();
    }

}
