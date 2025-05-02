package com.example.api_v01.controller;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import com.example.api_v01.dto.SuccessMessage;
import com.example.api_v01.handler.BadRequestException;
import com.example.api_v01.handler.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.api_v01.dto.ProductDTO;
import com.example.api_v01.model.Product;
import com.example.api_v01.service.product_service.ProductService;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Implementacion de los metodos aqui
    @GetMapping("/list")
    public ResponseEntity<?> getAllProducts() {
        SuccessMessage<List<Product>> successMessage = SuccessMessage.<List<Product>>builder()
                .status(HttpStatus.OK)
                .message("La lista de productos")
                .data(productService.getProducts())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(successMessage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable UUID id) throws NotFoundException {
        SuccessMessage<Product> successMessage = SuccessMessage.<Product>builder()
                .status(HttpStatus.OK)
                .message("Producto encontrado!!")
                .data(productService.getProduct(id))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(successMessage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable UUID id) throws NotFoundException {
        productService.deleteProduct(id); //llamada
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // respuesta 204 exito
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable UUID id, @RequestBody ProductDTO productDTO) throws NotFoundException {
        Product updateProduct = productService.updateProduct(id, productDTO);
        SuccessMessage<Product> successMessage = SuccessMessage.<Product>builder()
                .status(HttpStatus.OK)
                .message("Producto Actualizado correctamente!!")
                .data(updateProduct)
                .build();
        return ResponseEntity.ok(successMessage);
    }

    @PostMapping("/")
    public ResponseEntity<?> CreateProduct(@RequestBody ProductDTO productDTO) {
        Product product = productService.saveProduct(productDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(product.getId_Product())
                .toUri();

        SuccessMessage<Product> successMessage = SuccessMessage.<Product>builder()
                .status(HttpStatus.CREATED)
                .message("Producto creado correctamente!!")
                .data(product)
                .build();

        return ResponseEntity.created(location).body(successMessage);
    }

    @GetMapping("/")
    public ResponseEntity<?> getProductId() throws BadRequestException {
        throw new BadRequestException("Se necesita el ID del producto para buscarlo");
    }

    @PutMapping("/")
    public ResponseEntity<?> updateProductId() throws BadRequestException {
        throw new BadRequestException("Se necesita el ID del producto para actualizarlo");
    }

    @DeleteMapping("/")
    public ResponseEntity<?> deleteProductId() throws BadRequestException {
        throw new BadRequestException("Se necesita el ID del producto para eliminarlo");
    }

}