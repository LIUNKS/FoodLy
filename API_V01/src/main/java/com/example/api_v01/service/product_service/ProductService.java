package com.example.api_v01.service.product_service;

import com.example.api_v01.dto.entityLike.ProductDTO;
import com.example.api_v01.dto.entityLike.ProductStockDTO;
import com.example.api_v01.handler.NotFoundException;
import com.example.api_v01.model.Product;
import com.example.api_v01.model.enums.Category;

import java.util.List;
import java.util.UUID;


public interface ProductService {

    ProductDTO saveProduct(UUID id_admin,ProductDTO product) throws NotFoundException;

    void deleteProduct(UUID id) throws NotFoundException;

    List<ProductDTO> getProducts();

    ProductDTO getProductDTO(UUID id) throws NotFoundException;

    Product getProduct(UUID id) throws NotFoundException;

    ProductDTO updateProduct(UUID id, ProductDTO productDTO) throws NotFoundException;

    List<ProductDTO> getProductByCategory(Category categoria) throws NotFoundException;

    List<ProductDTO> getProductByName(String name) throws NotFoundException;

}
