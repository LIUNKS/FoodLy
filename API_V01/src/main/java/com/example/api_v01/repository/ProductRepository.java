package com.example.api_v01.repository;

import com.example.api_v01.model.Product;
import com.example.api_v01.model.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    @Query("SELECT p FROM Product p WHERE p.category=?1")
    Optional<List<Product>> findProductsByCategory(Category category);

    @Query("SELECT p FROM Product p WHERE p.name_product=?1")
    Optional<List<Product>> findProductByName(String name);

    @Query("SELECT p FROM Product p WHERE p.stock.id_product_stock=?1")
    Optional<Product> findProductStockById(UUID id_stock);

}

