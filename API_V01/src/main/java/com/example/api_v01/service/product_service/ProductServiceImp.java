package com.example.api_v01.service.product_service;

import com.example.api_v01.dto.ProductDTO;
import com.example.api_v01.model.Product;
import com.example.api_v01.repository.ProductRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImp implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product saveProduct(ProductDTO productDTO) {
        Product product = Product.builder()
                .name_product(productDTO.getName_product())
                .price(productDTO.getPrice())
                .additional_observation(productDTO.getAdditional_observation())
                .category(productDTO.getCategory())
                .stock(productDTO.getStock())
                .admin(productDTO.getAdmin())
                .build();
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(UUID id) {productRepository.deleteById(id);}

    @Override
    public List<Product> getProducts() {return  productRepository.findAll();}

    @Override
    public Product getProduct(UUID id) {
        return productRepository.findById(id).orElse(null);
    }
}
