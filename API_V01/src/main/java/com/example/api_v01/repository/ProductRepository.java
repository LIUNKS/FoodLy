package com.example.api_v01.repository;

import com.example.api_v01.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findByName_product(String name_product);
    List<Product> findByPrice(Double price);
    List<Product> findByCategory(String category);
    List<Product> findByAdditional_observation(String additional_observation);
    List<Product> findByStock(String stock);
    List<Product> findByAdmin(String admin);
    List<Product> findById_Product(UUID id_product);
}

