package com.example.api_v01.service.product_service;

import com.example.api_v01.dto.ProductDTO;
import com.example.api_v01.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


public interface ProductService {

    Product saveProduct(ProductDTO product);

    void deleteProduct(UUID id);

    List<Product> getProducts();

    Product getProduct(UUID id);
}
