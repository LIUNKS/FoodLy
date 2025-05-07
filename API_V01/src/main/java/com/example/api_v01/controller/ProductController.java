package com.example.api_v01.controller;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import com.example.api_v01.dto.SuccessMessage;
import com.example.api_v01.handler.BadRequestException;
import com.example.api_v01.handler.NotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.annotation.Resource;
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

    @Operation(
            summary = "Obtiene todos los productos",
            description = "Este endpoint devuelve una lista de productos disponibles en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Operación exitosa",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Resource.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontraron productos",
                    content = @Content
            )
    })

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

    @Operation(
            summary = "Obtiene un producto por ID",
            description = "Este endpoint devuelve un producto disponible en el sistema indicando su UUID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Operación exitosa",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Resource.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontro producto",
                    content = @Content
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable UUID id) throws NotFoundException {
        SuccessMessage<Product> successMessage = SuccessMessage.<Product>builder()
                .status(HttpStatus.OK)
                .message("Producto encontrado!!")
                .data(productService.getProduct(id))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(successMessage);
    }

    @Operation(
            summary = "Eliminar un producto por ID",
            description = "Este endpoint elimina un producto disponible en el sistema indicando su UUID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Operación exitosa",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Resource.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontro producto",
                    content = @Content
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable UUID id) throws NotFoundException {
        productService.deleteProduct(id); //llamada
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // respuesta 204 exito
    }

    @Operation(
            summary = "Actualizar un producto por ID",
            description = "Este endpoint devuelve un producto disponible en el sistema indicando su UUID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Operación exitosa",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Resource.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontro producto",
                    content = @Content
            )
    })
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

    @Operation(
            summary = "Crear un producto",
            description = "Este endpoint crea un producto en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Operación exitosa",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Resource.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Error",
                    content = @Content
            )
    })
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

    @Operation(
            summary = "Obtener producto",
            description = "Lanza una excepción en caso de que no se proporcione el ID del producto."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "ID del producto faltante. Se necesita el ID del producto para buscarlo.")
    })
    @GetMapping("/")
    public ResponseEntity<?> getProductId() throws BadRequestException {
        throw new BadRequestException("Se necesita el ID del producto para buscarlo");
    }

    @Operation(
            summary = "Actualizar producto",
            description = "Lanza una excepción en caso de que no se proporcione el ID del producto para la actualización."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "ID del producto faltante. Se necesita el ID del producto para actualizarlo.")
    })
    @PutMapping("/")
    public ResponseEntity<?> updateProductId() throws BadRequestException {
        throw new BadRequestException("Se necesita el ID del producto para actualizarlo");
    }

    @Operation(
            summary = "Eliminar producto",
            description = "Lanza una excepción en caso de que no se proporcione el ID del producto para eliminarlo."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "ID del producto faltante. Se necesita el ID del producto para eliminarlo.")
    })
    @DeleteMapping("/")
    public ResponseEntity<?> deleteProductId() throws BadRequestException {
        throw new BadRequestException("Se necesita el ID del producto para eliminarlo");
    }

}