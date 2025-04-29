package com.example.api_v01.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api_v01.dto.ProductDTO; 
import com.example.api_v01.model.Product;
import com.example.api_v01.service.product_service.ProductService;

import jakarta.validation.Valid; //validación de datos

@RestController
@RequestMapping("/api/product")
public class ProductoController {
    private final ProductService productService;

    //Constructor
    public ProductoController(ProductService productService) {
        this.productService = productService;
    }

    // Implementación de los métodos del controlador aquí
    @GetMapping
    public ResponseEntity<List<Product>> obtenerProductos() {
        return ResponseEntity.ok(productService.getProducts());
    }

    @PostMapping
    public ResponseEntity<Product> crearProducto(@Valid @RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.saveProduct(productDTO));
    }

}