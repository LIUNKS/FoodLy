package com.example.api_v01.controller;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import com.example.api_v01.dto.response.ProductResponseDTO;
import com.example.api_v01.dto.response.SuccessMessage;
import com.example.api_v01.handler.NotFoundException;
import com.example.api_v01.model.enums.Category;
import com.example.api_v01.utils.Tuple;
import com.example.api_v01.utils.UriGeneric;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.api_v01.dto.entityLike.ProductDTO;
import com.example.api_v01.service.product_service.ProductService;

@RestController
@RequestMapping("product")
public class ProductController {    //CONTROLADOR TESTEADO, LISTO PARA USAR

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    //Busca todos los productos por categoria
    @GetMapping("/list/category")
    public ResponseEntity<?> getAllProductsByCategory(@RequestParam Category category) throws NotFoundException{
        SuccessMessage <List<ProductDTO>> successMessage = SuccessMessage.<List<ProductDTO>>builder()
                .status(HttpStatus.OK)
                .message("Lista de productos por categoria")
                .data(productService.getProductByCategory(category))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(successMessage);
    }
    //Busca todos los productos que tengan el mismo nombre
    @GetMapping("/list/name")
    public ResponseEntity<?> getAllProductsByName(@RequestParam String name) throws NotFoundException {
        SuccessMessage <List<ProductDTO>> successMessage = SuccessMessage.<List<ProductDTO>>builder()
                .status(HttpStatus.OK)
                .message("Lista de productos por nombre")
                .data(productService.getProductByName(name))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(successMessage);
    }

    // Me devuelve la lista entera de productos
    @GetMapping("/list")
    public ResponseEntity<?> getAllProducts() {
        SuccessMessage<List<ProductDTO>> successMessage = SuccessMessage.<List<ProductDTO>>builder()
                .status(HttpStatus.OK)
                .message("La lista de productos")
                .data(productService.getProducts())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(successMessage);
    }

    // Me devuelve un producto buscado por su id
    @GetMapping("/{id_product}")
    public ResponseEntity<?> getProduct(@PathVariable UUID id_product) throws NotFoundException {
        SuccessMessage<ProductDTO> successMessage = SuccessMessage.<ProductDTO>builder()
                .status(HttpStatus.OK)
                .message("Producto encontrado!!")
                .data(productService.getProductDTO(id_product))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(successMessage);
    }

    // Elimina un producto junto con su stock, necesita el ID del producto
    @DeleteMapping("/{id_product}")
    public ResponseEntity<?> deleteProduct(@PathVariable UUID id_product) throws NotFoundException {
        productService.deleteProduct(id_product); //llamada
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // respuesta 204 exito
    }



    //Cambiar el tipo de DTO (No deberia tener ID)
    //actualiza el producto
    @PatchMapping("/{id_product}")
    public ResponseEntity<?> updateProduct(@PathVariable UUID id_product, @RequestBody ProductDTO productDTO) throws NotFoundException {
        ProductResponseDTO updateProduct = productService.updateProduct(id_product, productDTO);
        SuccessMessage<ProductResponseDTO> successMessage = SuccessMessage.<ProductResponseDTO>builder()
                .status(HttpStatus.OK)
                .message("Producto Actualizado correctamente!!")
                .data(updateProduct)
                .build();
        return ResponseEntity.ok(successMessage);
    }

    //Cambiar el tipo de DTO (No deberia tener ID)
    //agrega un producto, necesita el ID del admin que lo va a agregar
    @PostMapping("/{id_admin}")
    public ResponseEntity<?> CreateProduct(@PathVariable UUID id_admin,@RequestBody ProductDTO productDTO) throws NotFoundException {
        Tuple<ProductResponseDTO,UUID> product = productService.saveProduct(id_admin,productDTO);
        URI location = UriGeneric.GenereURI(
                "/product/{id_product}",
                product.getSecond()
        );
        SuccessMessage<ProductResponseDTO> successMessage = SuccessMessage.<ProductResponseDTO>builder()
                .status(HttpStatus.CREATED)
                .message("Producto creado correctamente!!")
                .data(product.getFirst())
                .build();
        return ResponseEntity.created(location).body(successMessage);
    }
}