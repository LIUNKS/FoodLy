package com.example.api_v01.controller;

import java.net.URI;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.api_v01.dto.ProductDTO;
import com.example.api_v01.model.Product;
import com.example.api_v01.service.product_service.ProductService;

import jakarta.validation.Valid; //data validation
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Implementation of the controller methods here
    @GetMapping("/listProducts")
    public ResponseEntity<?> getAllProducts() {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable UUID id) {
        if (productService.isExist(id)){
            Product product = productService.getProduct(id);
            return ResponseEntity.status(HttpStatus.OK).body(product);
        }
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PostMapping()
    public ResponseEntity<?> CreateProduct(@RequestBody ProductDTO productDTO) {
        Product product = productService.saveProduct(productDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(product.getId_Product())
                .toUri();
        return ResponseEntity.created(location).body(product);
    }

}