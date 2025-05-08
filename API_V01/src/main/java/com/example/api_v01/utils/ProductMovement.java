package com.example.api_v01.utils;

import com.example.api_v01.dto.ProductDTO;
import com.example.api_v01.model.Admin;
import com.example.api_v01.model.Product;
import com.example.api_v01.model.ProductStock;


public class ProductMovement {
    public static Product createProductAndStock (Admin admin,ProductDTO productDTO) {
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
                .admin(admin)
                .build();
    }

    public static Product ValidateProduct(Product existingProduct,ProductDTO productDTO) {
        if (productDTO.getName_product() != null) {existingProduct.setName_product(productDTO.getName_product());}
        if (productDTO.getPrice() != null) {existingProduct.setPrice(productDTO.getPrice());}
        if (productDTO.getAdditional_observation() != null) {existingProduct.setAdditional_observation(productDTO.getAdditional_observation());}
        if (productDTO.getCategory() != null) {existingProduct.setCategory(productDTO.getCategory());}
        return existingProduct;
    }
}
