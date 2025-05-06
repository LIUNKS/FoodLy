package com.example.api_v01.repository;

import com.example.api_v01.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    @Query("SELECT p FROM Product p WHERE p.stock.id_product_stock = ?1")
    Optional<Product> findProductById_Stock(UUID id);
}

