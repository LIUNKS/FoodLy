package com.example.api_v01.service.product_service;

import com.example.api_v01.dto.ProductDTO;
import com.example.api_v01.model.Product;
import com.example.api_v01.repository.ProductRepository;
import com.example.api_v01.utils.ProductMovement;
import lombok.RequiredArgsConstructor;

import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImp implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Boolean isExist(UUID id) {
        return productRepository.existsById(id);
    }

    @Override
    public Product saveProduct(ProductDTO productDTO) {
        Product product = ProductMovement.createProductAndStock(productDTO);
        product = productRepository.save(product);
        return product;
    }

    @Override
    public void deleteProduct(UUID id) {productRepository.deleteById(id);}

    @Override
    public List<Product> getProducts() {return  productRepository.findAll();}

    @Override
    public Product getProduct(UUID id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product updateProduct(UUID id, ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado mamoso"));
        // Actualiza solo los campos permitidos
        existingProduct.setName_product(productDTO.getName_product());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setAdditional_observation(productDTO.getAdditional_observation());

        return productRepository.save(existingProduct);
    }
}
